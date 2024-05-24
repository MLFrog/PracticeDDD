package com.mlfrog.practiceddd.domain.board;

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
    public void 게시글저장테스트() {
    	Board tmp = this.factory.getInstance();
    	
        tmp.setNickname("닉네임");
        tmp.setTitle("제목입니다.");
        tmp.setContent("내용입니다.");
        System.out.println(tmp);
        tmp.insert(this.repository);
    }
}
