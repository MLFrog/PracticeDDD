package com.mlfrog.practiceddd.infrastructure.jpa.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity //테이블과 링크될 클래스임을 나타낸다.
@Table(name = "board")
public class BoardJpaEntity {

    @Id //테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 생성 규칙을 나타냅니다.
    @Column(name = "board_id")
    private Long boardId;
    
    @Column(name = "nickname")
    private String nickname;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "content")
    private String content;

    @Column(name = "expir_yn")
    private String expirYn;
    
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
