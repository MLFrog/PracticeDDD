package com.mlfrog.practiceddd.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "email_verify", schema = "public")
public class EmailVerifyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    private Long rowId;

    @Column(name = "member_row_id")
    private String memberLoginId;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expire_at")
    private Timestamp expireAt;
}

