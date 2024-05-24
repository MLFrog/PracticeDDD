package com.mlfrog.practiceddd.domain.board.convert.impl;

import java.time.Instant;
import java.util.Optional;

import com.mlfrog.practiceddd.domain.board.Board;
import com.mlfrog.practiceddd.domain.board.BoardFactory;
import com.mlfrog.practiceddd.domain.board.convert.BoardConverter;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.BoardJpaEntity;

public class JpaBoardConverter implements BoardConverter<BoardJpaEntity>{
	
	private final  BoardFactory factory = new BoardFactory();
	
	@Override
	public Board convert(BoardJpaEntity entity) {
    	Board obj = this.factory.getInstance();

        obj.setBoardId(Optional.ofNullable(entity.getBoardId()).orElse(null));
        obj.setNickname(Optional.ofNullable(entity.getNickname().toString()).orElse(null));
        obj.setTitle(Optional.ofNullable(entity.getTitle().toString()).orElse(null));
        obj.setContent(Optional.ofNullable(entity.getContent().toString()).orElse(null));
        obj.setExpirYn(Optional.ofNullable(entity.getExpirYn().toString()).orElse(null));
		obj.setCreatedAt(Instant.now());
		obj.setUpdatedAt(Instant.now());

        return obj;
    }
}
