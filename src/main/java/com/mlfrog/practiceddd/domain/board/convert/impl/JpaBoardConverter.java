package com.mlfrog.practiceddd.domain.board.convert.impl;

import java.time.Instant;
import java.util.Optional;

import com.mlfrog.practiceddd.domain.board.Board;
import com.mlfrog.practiceddd.domain.board.BoardFactory;
import com.mlfrog.practiceddd.domain.board.BoardId;
import com.mlfrog.practiceddd.domain.board.convert.BoardConverter;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.BoardJpaEntity;

public class JpaBoardConverter implements BoardConverter<BoardJpaEntity>{
	
	private final  BoardFactory factory = new BoardFactory();
	
	@Override
	public Board convert(BoardJpaEntity entity) {
    	Board obj = this.factory.getInstance();

    	obj.setBoardId(Optional.ofNullable(BoardId.of(entity.getBoardId())).orElse(BoardId.of(0)));
		obj.setNickname(String.valueOf(entity.getNickname()));
		obj.setTitle(String.valueOf(entity.getTitle()));
		obj.setContent(String.valueOf(entity.getContent()));
		obj.setExpirYn(Optional.ofNullable(entity.getExpirYn()).orElse("N"));
		obj.setCreatedAt(Optional.ofNullable(entity.getCreatedAt().toInstant()).orElse(Instant.now()));
		obj.setUpdatedAt(Optional.ofNullable(entity.getUpdatedAt().toInstant()).orElse(Instant.now())); 
	          
        return obj;
    }
}
