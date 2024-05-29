package com.mlfrog.practiceddd.domain.member;

import com.mlfrog.practiceddd.domain.common.DomainEntity;
import com.mlfrog.practiceddd.domain.member.roles.Roles;
import lombok.Data;
import org.springframework.util.Assert;

import java.time.Instant;

@Data
@DomainEntity
public class Member {
    private Long rowId;
    private String loginId;
    private String password;
    private String email;
    private String phoneNumber;
    private String nickname;
    private String realname;
    private Boolean emailVerifiedTp;
    private Roles roles;
    private String agreeTp;
    private Instant createdAt;
    private Instant updatedAt;

    //멤버 정보 저장
    public void save(MemberRepository repository) {
        Assert.notNull(this.loginId, "Member loginId 값은 null일 수 없습니다.");

        repository.save(this);
    }

    //중복 회원 체크
    public Member checkDupId(MemberRepository repository){
        Assert.notNull(this.loginId, "loginId는 필수값 입니다.");

        return repository.findOnebyId(this.loginId);
    }
}
