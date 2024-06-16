package com.mlfrog.practiceddd.domain.board;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.util.Assert;

import com.mlfrog.practiceddd.domain.common.DomainEntity;

import lombok.Data;

@Data
@DomainEntity
public class Board {
    private BoardId boardId;
    private String nickname;
    private String title;
    private String content;
    private String expirYn;
    private Instant createdAt;
    private Instant updatedAt;

	/**
	 * 저장
	 * 
	 * @param repository
	 */
    public void save(BoardRepository repository) 
    {
        Assert.notNull(this.boardId, "Board ID 값은 null일 수 없습니다.");

        repository.save(this);
    }
    
	/**
	 * 게시글 리스트 조회
	 * 
	 * @param repository
	 * @return
	 */
    public List<Board> findAll(BoardRepository repository)
    {
    	Assert.notNull(this.boardId, "Board ID 값은 null일 수 없습니다.");
    	
    	List<Board> boardList = repository.findAllByOrderByBoardIdAsc();
    	
    	return boardList;
    }
    
	/**
	 * BoardId 값으로 데이터 fetch
	 * 
	 * @param repository
	 * @return
	 */
    public void fetchById(BoardRepository repository) {
		Assert.notNull(this.boardId, "Board ID 값은 null일 수 없습니다.");
		
		Board obj = repository.findByBoardId(this.boardId.getValue());
		
		this.boardId = Optional.ofNullable(obj.getBoardId()).orElse(BoardId.of(0));
		this.nickname = String.valueOf(obj.getNickname());
		this.title =  String.valueOf(obj.getTitle());
		this.content = String.valueOf(obj.getContent());
		this.expirYn = Optional.ofNullable(obj.getExpirYn()).orElse("N");
		this.createdAt = Optional.ofNullable(obj.getUpdatedAt()).orElse(Instant.now());
		this.updatedAt = Optional.ofNullable(obj.getUpdatedAt()).orElse(Instant.now());
	}


}
