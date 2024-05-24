package com.mlfrog.practiceddd.domain.board.repository.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
	public void insert(Board board)
	{
    	Assert.notNull(board, "회원 엔티티는 Null일 수 없습니다.");
    	System.out.println("JpaBoardRepository의 insert까지는 탐" + board);
        this.jpaRepository.save(convert(board));
	}
    
    @Override
	public void update(Board board)
	{

	}
	
    @Override
	public void delete(Long boardId)
	{
  
	}
	
    @Override
	public List<Board> getAllBoard()
	{
    	return null;
	}
	
    @Override
	public Board getOneBoardByBoardId(Long boardId)
	{
    	return null;
	}
    
    private BoardJpaEntity convert(Board data) {
    	BoardJpaEntity obj = new BoardJpaEntity();
    	
        obj.setBoardId(Optional.ofNullable(data.getBoardId()).orElse(null));
        obj.setNickname(Optional.ofNullable(data.getNickname().toString()).orElse(null));
        obj.setTitle(Optional.ofNullable(data.getTitle().toString()).orElse(null));
        obj.setContent(Optional.ofNullable(data.getContent().toString()).orElse(null));
        obj.setExpirYn(Optional.ofNullable(data.getExpirYn()).orElse(null));
        obj.setCreatedAt(Timestamp.from(Instant.now()));
        obj.setUpdatedAt(Timestamp.from(Instant.now()));
        
        return obj;
    }

}
