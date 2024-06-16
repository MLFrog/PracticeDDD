
package com.mlfrog.practiceddd.application.board.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlfrog.practiceddd.application.board.BoardService;
import com.mlfrog.practiceddd.domain.board.Board;
import com.mlfrog.practiceddd.domain.board.BoardFactory;
import com.mlfrog.practiceddd.domain.board.BoardId;
import com.mlfrog.practiceddd.domain.board.BoardRepository;
import com.mlfrog.practiceddd.domain.board.convert.BoardConverter;
import com.mlfrog.practiceddd.domain.board.convert.impl.JpaBoardConverter;
import com.mlfrog.practiceddd.domain.board.repository.impl.JpaBoardRepository;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.BoardJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.BoardJpaRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private BoardRepository repository;

    private BoardConverter<BoardJpaEntity> converter;

    private BoardFactory factory;

    @Autowired
    private BoardJpaRepository jpaRepository;

    @PostConstruct
    public void init() {
        this.factory = new BoardFactory();
        this.converter = new JpaBoardConverter();
        this.repository = new JpaBoardRepository(this.jpaRepository, this.converter);
    }

    // 게시글 저장 
    public void save(Board data, String state) {
    	
    	switch(state) {
        case "I" : 
        	
        	Board boardInsert = this.factory.getInstance();
        	
        	boardInsert.setNickname(data.getNickname());
        	boardInsert.setTitle(data.getTitle());
        	boardInsert.setContent(data.getContent());
        	
        	boardInsert.save(this.repository);
            
            break;
             
        case "U" : 
        	
        	Board boardUpdate = repository.findByBoardId(data.getBoardId().getValue()); 
        	
        	boardUpdate.setContent(data.getContent());
        	boardUpdate.setUpdatedAt(Instant.now());
        	
        	repository.save(boardUpdate);
        	
            break;
            
        case "D" : 
        	
        	Board boardDelete = repository.findByBoardId(data.getBoardId().getValue());
        	
        	boardDelete.setTitle("삭제된 게시물입니다.");
        	boardDelete.setExpirYn("Y");
        	boardDelete.setUpdatedAt(Instant.now());
        	
        	repository.save(boardDelete);
        	
            break;         
    	}
    	
    }

    
    // 게시글 목록 조회
    public List<Board> list() {
    	
        return repository.findAllByOrderByBoardIdAsc();
        
    }

    // 특정 게시글 상세보기
    public Board board(BoardId boardId) {
    	
        return repository.findByBoardId(boardId.getValue());
        
    }

}