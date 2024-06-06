package com.mlfrog.practiceddd.domain.email.repository.impl;

import com.mlfrog.practiceddd.domain.email.Email;
import com.mlfrog.practiceddd.domain.email.EmailRepository;
import com.mlfrog.practiceddd.domain.email.convert.EmailVerifyConverter;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.EmailVerifyJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.EmailVerifyJpaRepository;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class JpaEmailRepository implements EmailRepository {

    private final EmailVerifyJpaRepository jpaRepository;
    private final EmailVerifyConverter<EmailVerifyJpaEntity> converter;

    public JpaEmailRepository(EmailVerifyJpaRepository jpaRepository, EmailVerifyConverter<EmailVerifyJpaEntity> converter) {
        Assert.notNull(jpaRepository, "jpaRepository는 null일 수 없습니다.");
        Assert.notNull(converter, "converter는 null일 수 없습니다.");
        this.jpaRepository = jpaRepository;
        this.converter = converter;
    }

    @Override
    public void save(Email verify) {
        Assert.notNull(verify, "이메일인증 엔티티는 Null일 수 없습니다.");

        this.jpaRepository.save(convert(verify));
    }

    private EmailVerifyJpaEntity convert(Email data) {
        EmailVerifyJpaEntity obj = new EmailVerifyJpaEntity();

        obj.setRowId(Optional.ofNullable(data.getRowId()).orElse(null));
        obj.setMemberLoginId(Optional.ofNullable(data.getLoginId().toString()).orElse(null));
        obj.setCreatedAt(Timestamp.from(Instant.now()));
        obj.setToken(Optional.ofNullable(data.getToken()).orElse(null));

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expirationDateTime = currentDateTime.plus(3, ChronoUnit.YEARS);
        Instant expirationInstant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();

        obj.setExpireAt(Timestamp.from(expirationInstant));

        return obj;
    }
}
