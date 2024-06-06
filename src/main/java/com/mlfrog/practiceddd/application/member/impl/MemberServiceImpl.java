package com.mlfrog.practiceddd.application.member.impl;


import com.mlfrog.practiceddd.application.emailVerify.EmailVerifyService;
import com.mlfrog.practiceddd.application.encrypt.HashConfig;
import com.mlfrog.practiceddd.application.member.MemberService;
import com.mlfrog.practiceddd.domain.email.Email;
import com.mlfrog.practiceddd.domain.email.EmailFactory;
import com.mlfrog.practiceddd.domain.email.EmailRepository;
import com.mlfrog.practiceddd.domain.email.convert.EmailVerifyConverter;
import com.mlfrog.practiceddd.domain.email.convert.impl.JpaEmailVerifyConverter;
import com.mlfrog.practiceddd.domain.email.repository.impl.JpaEmailRepository;
import com.mlfrog.practiceddd.domain.member.Member;
import com.mlfrog.practiceddd.domain.member.MemberFactory;
import com.mlfrog.practiceddd.domain.member.MemberRepository;
import com.mlfrog.practiceddd.domain.member.convert.MemberConverter;
import com.mlfrog.practiceddd.domain.member.convert.impl.JpaMemberConverter;
import com.mlfrog.practiceddd.domain.member.repository.impl.JpaMemberRepository;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.EmailVerifyJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.EmailVerifyJpaRepository;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.MemberJpaRepository;
import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private MemberFactory factory;

    private MemberRepository repository;

    private MemberConverter<MemberJpaEntity> converter;

    private final MemberJpaRepository jpaRepository;
    private EmailFactory emailFactory;

    private EmailRepository emailRepository;

    private EmailVerifyConverter<EmailVerifyJpaEntity> emailconverter;

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
        this.emailFactory = new EmailFactory();
        this.emailconverter = new JpaEmailVerifyConverter();
        this.emailRepository = new JpaEmailRepository(this.emailJpaRepository, this.emailconverter);
    }

    /**
     * 회원가입 - 회원정보 입력및 저장
     */
    public void signUp(SignUpObject data) {
        Member member = this.factory.getInstance();
        Email verify = this.emailFactory.getInstance();

        member.setLoginId(data.getLoginId());
        member.setPassword(encryptPassword(data.getPassword()));
        member.setEmail(data.getEmail());
        member.setPhoneNumber(data.getPhoneNumber());
        member.setNickname(data.getNickname());
        member.setRealname(data.getRealname());
        member.setRoles(data.getRoles());
        member.setAgreeTp(data.getAgreeTp());
        member.save(this.repository);
        String token = createToken();
        verify.setLoginId(data.getLoginId());
        verify.setToken(token);
        verify.save(this.emailRepository);

        sendVerificationEmail(data.getEmail(),token);
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

    public String createToken(){
        String token = UUID.randomUUID().toString();
        return token;
    }

    private void sendVerificationEmail(String email, String token) {
        // 이메일 전송 로직 구현
        String verificationLink = "http://localhost:9999/verify?token=" + token;
        String emailContent = "<html>"
                + "<body>"
                + "<p>이메일 인증을 위해 아래 버튼을 클릭하세요:</p>"
                + "<a href=\"" + verificationLink + "\" style=\"display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #007bff; text-decoration: none;\">이메일 인증</a>"
                + "</body>"
                + "</html>";
        emailVerifyService.sendEmail(email, "이메일 인증", emailContent);
    }
}

