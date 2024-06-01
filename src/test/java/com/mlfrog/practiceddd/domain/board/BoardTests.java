package com.mlfrog.practiceddd.domain.board;

import java.sql.Timestamp;
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
    public void 게시글테스트() {
    	//게시글 저장
    	Board board1 = this.factory.getInstance();
    	
    	board1.setNickname("닉네임");
    	board1.setTitle("제목입니다.");
    	board1.setContent("내용입니다.");
    	board1.setCreatedAt(Instant.now());

    	board1.save(this.repository);
        
    	board1 = repository.findByBoardId((long)1);
    	System.out.println("게시글 생성 테스트 : " + board1);
        
    	//게시글 목록 조회
        List<Board> boardList = repository.findAll();
        System.out.println("게시글 목록 조회 테스트 : " + boardList.size());

        //게시글 조회
        Board board2 = repository.findByBoardId((long)1);
        System.out.println("게시글 조회 테스트 : " + board2);
        
        //게시글 수정        
        Board board3 = repository.findByBoardId((long)1);
        board3.setTitle("수정된 제목입니다.");
        
        repository.save(board3);
        
        board3 = repository.findByBoardId((long)1);
        System.out.println("게시글 수정 테스트 : " + board3 );
        
        //게시글 삭제
        Board board4 = repository.findByBoardId((long)1);
        board4.setExpirYn("Y");
        board4.setTitle("삭제된 게시물입니다.");
        
        repository.save(board4);
        
        board4 = repository.findByBoardId((long)1);
        System.out.println("게시글 삭제 테스트 : " +  board4.getExpirYn());
    }
   

}
