package com.mlfrog.practiceddd.domain.board.repository.impl;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.mlfrog.practiceddd.domain.board.Board;
import com.mlfrog.practiceddd.domain.board.BoardRepository;
import com.mlfrog.practiceddd.domain.board.convert.BoardConverter;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.BoardJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.BoardJpaRepository;


public class JpaBoardRepository implements BoardRepository{

    private final BoardJpaRepository jpaRepository;
    private final BoardConverter<BoardJpaEntity> converter;
    
    public JpaBoardRepository(BoardJpaRepository jpaRepository, BoardConverter<BoardJpaEntity> converter) {
        Assert.notNull(jpaRepository, "jpaRepository는 null일 수 없습니다.");
        Assert.notNull(converter, "converter는 null일 수 없습니다.");
        this.jpaRepository = jpaRepository;
        this.converter = converter;
    }
	
    @Override
	public void save(Board board)
	{
    	Assert.notNull(board,  "board는 Null일 수 없습니다.");
    	this.jpaRepository.save(convert(board));
	}  
	
    @Override
	public List<Board> findAllByOrderByBoardIdAsc()
	{
    	return Optional.ofNullable(this.jpaRepository.findAllByOrderByBoardIdAsc())
    			.map(boardList -> boardList.stream()
    					.map(this.converter::convert)
			 			.collect(Collectors.toList()))
    			.orElse(null);
	}
	
    @Override
	public Board findByBoardId(Long boardId)
	{
    	Assert.notNull(boardId,  "boardId는 Null일 수 없습니다.");
    	
    	return Optional.ofNullable(this.jpaRepository.findByBoardId(boardId))
    			.map(this.converter::convert)
				.orElse(null);
	}
    
    private BoardJpaEntity convert(Board data) {
    	
    	BoardJpaEntity obj = new BoardJpaEntity();
    	
    	obj.setBoardId(Optional.ofNullable(Long.valueOf(data.getBoardId().toString())).orElse(0l));
		obj.setNickname(String.valueOf(data.getNickname()));
		obj.setTitle(String.valueOf(data.getTitle()));
		obj.setContent(String.valueOf(data.getContent()));
		obj.setExpirYn(Optional.ofNullable(data.getExpirYn()).orElse("N"));
		obj.setCreatedAt(Optional.ofNullable(data.getCreatedAt()).map(Timestamp::from).orElse(Timestamp.from(Instant.now())));
        obj.setUpdatedAt(Optional.ofNullable(data.getUpdatedAt()).map(Timestamp::from).orElse(Timestamp.from(Instant.now()))); 

        return obj;
    }
    
}
