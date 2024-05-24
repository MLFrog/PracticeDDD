package com.mlfrog.practiceddd.application.member.impl;


import com.mlfrog.practiceddd.application.encrypt.HashConfig;
import com.mlfrog.practiceddd.application.member.MemberService;
import com.mlfrog.practiceddd.domain.member.Member;
import com.mlfrog.practiceddd.domain.member.MemberFactory;
import com.mlfrog.practiceddd.domain.member.MemberRepository;
import com.mlfrog.practiceddd.domain.member.convert.MemberConverter;
import com.mlfrog.practiceddd.domain.member.convert.impl.JpaMemberConverter;
import com.mlfrog.practiceddd.domain.member.repository.impl.JpaMemberRepository;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.MemberJpaRepository;
import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private MemberFactory factory;

    private MemberRepository repository;

    private MemberConverter<MemberJpaEntity> converter;

    private final MemberJpaRepository jpaRepository;

    @Autowired
    private HashConfig hashConfig;

    @PostConstruct
    public void init() {
        this.factory = new MemberFactory();
        this.converter = new JpaMemberConverter();
        this.repository = new JpaMemberRepository(this.jpaRepository, this.converter);
    }

    /**
     * 회원가입 - 회원정보 입력및 저장
     */
    public void signUp(SignUpObject data) {
        Member member = this.factory.getInstance();

        member.setLoginId(data.getLoginId());
        member.setPassword(encryptPassword(data.getPassword()));
        member.setEmail(data.getEmail());
        member.setPhoneNumber(data.getPhoneNumber());
        member.setNickname(data.getNickname());
        member.setRealname(data.getRealname());
        member.setRoles(data.getRoles());

        member.save(this.repository);
    }

    /**
     * 회원가입 - 중복 아이디 확인
     */
    public boolean comfirmDupId(SignUpObject data){
        boolean dupId = true;

        Member member = this.factory.getInstance();
        member.setLoginId((data.getLoginId()));

        if(member.checkDupId(this.repository)!=null)
            dupId = false;

        return dupId;
    }

    public String encryptPassword(String password){
        String pass = "";
        try {
            pass = hashConfig.hashPassword(password, hashConfig.generateSalt(16));
        }catch (Exception e){
            log.error("Password encryption failed: " + e.getMessage());
        }
        return pass;
    }
}

