package com.mlfrog.practiceddd.domain.board;

import java.time.Instant;

import org.springframework.util.Assert;

import com.mlfrog.practiceddd.domain.common.DomainEntity;

import lombok.Data;

@Data
@DomainEntity
public class Board {
    private Long boardId;
    private String nickname;
	private String title;
	private String content;
    private String expirYn;
    private Instant createdAt;
    private Instant updatedAt;

    public void insert(BoardRepository repository) {
        Assert.notNull(this.title, "Boardtitle 값은 null일 수 없습니다.");
        System.out.println("Board의 insert까지는 탐" + this);
        repository.insert(this);
    }
}
