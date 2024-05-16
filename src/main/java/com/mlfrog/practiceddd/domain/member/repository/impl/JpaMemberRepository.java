package com.mlfrog.practiceddd.domain.member.repository.impl;

import com.mlfrog.practiceddd.domain.member.Member;
import com.mlfrog.practiceddd.domain.member.MemberRepository;
import com.mlfrog.practiceddd.domain.member.convert.MemberConverter;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.MemberJpaRepository;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final MemberJpaRepository jpaRepository;
    private final MemberConverter<MemberJpaEntity> converter;

    public JpaMemberRepository(MemberJpaRepository jpaRepository, MemberConverter<MemberJpaEntity> converter) {
        Assert.notNull(jpaRepository, "jpaRepository는 null일 수 없습니다.");
        Assert.notNull(converter, "converter는 null일 수 없습니다.");
        this.jpaRepository = jpaRepository;
        this.converter = converter;
    }

    @Override
    public void save(Member member) {
        Assert.notNull(member, "회원 엔티티는 Null일 수 없습니다.");

        this.jpaRepository.save(convert(member));
    }

    @Override
    public Member getOneMemberByRowId(Long id) {
        return null;
    }

    @Override
    public Member getOneMemberByMemberId(String loginId) {
        return null;
    }

    @Override
    public List<Member> getAllMember() {
        return null;
    }

    @Override
    public Boolean isMember(String loginId) {
        return null;
    }

    @Override
    public String getMemberIdByEmail(String email) {
        return null;
    }

    @Override
    public Boolean deleteMember(String loginId) {
        return null;
    }

    private MemberJpaEntity convert(Member data) {
        MemberJpaEntity obj = new MemberJpaEntity();

        obj.setRowId(Optional.ofNullable(data.getRowId()).orElse(null));
        obj.setLoginId(Optional.ofNullable(data.getLoginId().toString()).orElse(null));
        obj.setPassword(Optional.ofNullable(data.getPassword().toString()).orElse(null));
        obj.setEmail(Optional.ofNullable(data.getEmail().toString()).orElse(null));
        obj.setPhoneNumber(Optional.ofNullable(data.getPhoneNumber().toString()).orElse(null));
        obj.setNickname(Optional.ofNullable(data.getNickname().toString()).orElse(null));
        obj.setRealname(Optional.ofNullable(data.getRealname().toString()).orElse(null));
        obj.setEmailVerifiedTp(Optional.ofNullable(data.getEmailVerifiedTp()).orElse(false));
        obj.setRoles(Optional.ofNullable(data.getRoles().getCode()).orElse(null));
        obj.setCreatedAt(Timestamp.from(Instant.now()));
        obj.setUpdatedAt(Timestamp.from(Instant.now()));

        return obj;
    }
}
