package com.mlfrog.practiceddd.domain.email;

import com.mlfrog.practiceddd.domain.common.DomainEntity;
import lombok.Data;
import org.springframework.util.Assert;

import java.time.Instant;

@Data
@DomainEntity
public class Email {
    private Long rowId;
    private String loginId;
    private String token;
    private Instant expiereAt;


    //토큰 정보 저장
    public void save(EmailRepository repository) {
        Assert.notNull(this.loginId, "token loginId 값은 null일 수 없습니다.");

        repository.save(this);
    }

}
