package com.mlfrog.practiceddd.domain.board;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mlfrog.practiceddd.domain.board.convert.BoardConverter;
import com.mlfrog.practiceddd.domain.board.convert.impl.JpaBoardConverter;
import com.mlfrog.practiceddd.domain.board.repository.impl.JpaBoardRepository;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.BoardJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.BoardJpaRepository;

import jakarta.annotation.PostConstruct;

@SpringBootTest
public class BoardTests {
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

    @Test
    public void 게시글저장() {
    	Board board = this.factory.getInstance();
    	
    	board.setBoardId(BoardId.of(0));
    	board.setNickname("닉네임");
    	board.setTitle("제목입니다.");
    	board.setContent("내용입니다.");
    	
    	board.save(this.repository);
        
    	board = repository.findByBoardId(1l);
    	System.out.println("게시글 생성 테스트 : " + board);
        
    }
   
    @Test
    public void 게시글목록조회() {
    	
    	 List<Board> boardList = repository.findAllByOrderByBoardIdAsc();
         System.out.println("게시글 목록 조회 테스트 : " + boardList.size());

    }

    @Test
    public void 게시글조회() {
    	
    	 Board board = repository.findByBoardId(1l);
         System.out.println("게시글 조회 테스트 : " + board);
         
    }
    
    @Test 
    public void 게시글수정() {
	   
	   Board board = repository.findByBoardId(1l);
       
       board.setContent("수정된 제목입니다.");
       
       repository.save(board);
       
       board = repository.findByBoardId(1l);
       System.out.println("게시글 수정 테스트 : " + board );
    }
    
    @Test
    public void 게시글삭제() {
    	 Board board = repository.findByBoardId(1l);
         
         board.setExpirYn("Y");
         board.setTitle("삭제된 게시물입니다.");
         
         repository.save(board);
         
         board = repository.findByBoardId(1l);
         System.out.println("게시글 삭제 테스트 : " +  board.getExpirYn());
    }
}
