package com.mlfrog.practiceddd.application.member.impl;


import com.mlfrog.practiceddd.application.emailVerify.EmailVerifyService;
import com.mlfrog.practiceddd.application.encrypt.HashConfig;
import com.mlfrog.practiceddd.application.member.MemberService;
import com.mlfrog.practiceddd.domain.member.Member;
import com.mlfrog.practiceddd.domain.member.MemberFactory;
import com.mlfrog.practiceddd.domain.member.MemberRepository;
import com.mlfrog.practiceddd.domain.member.convert.MemberConverter;
import com.mlfrog.practiceddd.domain.member.convert.impl.JpaMemberConverter;
import com.mlfrog.practiceddd.domain.member.repository.impl.JpaMemberRepository;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.EmailVerifyJpaRepository;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.MemberJpaRepository;
import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private MemberFactory factory;

    private MemberRepository repository;

    private MemberConverter<MemberJpaEntity> converter;

    private final MemberJpaRepository jpaRepository;

    private final EmailVerifyJpaRepository emailJpaRepository;

    @Autowired
    private HashConfig hashConfig;

    @Autowired
    private EmailVerifyService emailVerifyService;

    @PostConstruct
    public void init() {
        this.factory = new MemberFactory();
        this.converter = new JpaMemberConverter();
        this.repository = new JpaMemberRepository(this.jpaRepository, this.converter);
    }


    /**
     * 회원가입 - 회원정보 입력및 저장
     */
    @Transactional
    public void signUp(SignUpObject data) {
        try {
            Member member = this.factory.getInstance();

            //멤버 데이터 저장
            member.setLoginId(data.getLoginId());
            member.setPassword(encryptPassword(data.getPassword()));
            member.setEmail(data.getEmail());
            member.setPhoneNumber(data.getPhoneNumber());
            member.setNickname(data.getNickname());
            member.setRealname(data.getRealname());
            member.setRoles(data.getRoles());
            member.setAgreeTp(data.getAgreeTp());
            member.save(this.repository);

            //이메일 인증테이블 저장(3년 만료데이터 등) -> 가입 이메일로 인증메일 발송
            emailVerifyService.saveSendEmail(data);
        } catch (Exception e) {
            // 필요한 로깅 및 예외 처리
            throw new RuntimeException("회원가입 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * 회원가입 - 중복 아이디 확인w
     */
    public boolean comfirmDupId(SignUpObject data){
        boolean dupId = true;

        Member member = this.factory.getInstance();
        member.setLoginId((data.getLoginId()));

        if(member.checkDupId(this.repository)!=null)
            dupId = false;

        return dupId;
    }

    public void emailVerifyComplete(String id) {
        //1
        Member member = repository.findOnebyId(id);
        member.setEmailVerifiedTp(true);
        member.save(this.repository);

        //2
//        MemberJpaEntity entity = this.jpaRepository.findByLoginId(id);
//        entity.setEmailVerifiedTp(true);
//        this.jpaRepository.save(entity);
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
    /**
     * 회원정보 수정
     */
//    public String updateMemberData(SignUpObject data){
//
//    }



}

