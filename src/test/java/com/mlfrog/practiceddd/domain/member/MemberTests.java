package com.mlfrog.practiceddd.domain.member;

import com.mlfrog.practiceddd.domain.member.convert.MemberConverter;
import com.mlfrog.practiceddd.domain.member.convert.impl.JpaMemberConverter;
import com.mlfrog.practiceddd.domain.member.repository.impl.JpaMemberRepository;
import com.mlfrog.practiceddd.domain.member.roles.Roles;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.MemberJpaRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTests {
    private MemberRepository repository;

    private MemberConverter<MemberJpaEntity> converter;

    private MemberFactory factory;

    @Autowired
    private MemberJpaRepository jpaRepository;

    @PostConstruct
    public void init() {
        this.factory = new MemberFactory();
        this.converter = new JpaMemberConverter();
        this.repository = new JpaMemberRepository(this.jpaRepository, this.converter);
    }

    @Test
    public void 회원정보저장테스트() {
        Member tmp = this.factory.getInstance();

        tmp.setLoginId("test2");
        tmp.setPassword("1234");
        tmp.setEmail("example2@gmail.com");
        tmp.setPhoneNumber("01022222222");
        tmp.setNickname("id2222");
        tmp.setRealname("박준모");
        tmp.setEmailVerifiedTp(false);
        tmp.setRoles(Roles.ADMIN);

        tmp.save(this.repository);
    }
}
