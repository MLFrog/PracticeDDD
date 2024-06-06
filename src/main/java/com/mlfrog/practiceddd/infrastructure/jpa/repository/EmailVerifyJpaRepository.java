package com.mlfrog.practiceddd.infrastructure.jpa.repository;

import com.mlfrog.practiceddd.infrastructure.jpa.entity.EmailVerifyJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("EmailVerifyJpaRepository")
public interface EmailVerifyJpaRepository extends JpaRepository<EmailVerifyJpaEntity, Long> {
    EmailVerifyJpaEntity findByToken(String token);
}
