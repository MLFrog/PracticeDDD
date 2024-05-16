package com.mlfrog.practiceddd.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "member_closed", schema = "public")
public class MemberClosedJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "row_id")
    private Integer rowId;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "realname")
    private String realname;

    @Column(name = "email_verified_tp")
    private boolean emailVerifiedTp;

    @Column(name = "roles")
    private String roles;

    @Column(name = "closed_reason")
    private String closedReason;

    @Column(name = "closed_at")
    private boolean closedAt;

    @Column(name = "expire_at")
    private boolean expireAt;
}

