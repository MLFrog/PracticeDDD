package com.mlfrog.practiceddd.application.emailVerify.impl;

import com.mlfrog.practiceddd.application.emailVerify.EmailVerifyService;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.EmailVerifyJpaEntity;
import com.mlfrog.practiceddd.infrastructure.jpa.repository.EmailVerifyJpaRepository;
import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmailVerifyServiceImpl implements EmailVerifyService {

    @Autowired
    private JavaMailSender mailSender;
    private final EmailVerifyJpaRepository emailJpaRepository;

    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EmailVerifyJpaEntity checkToken(String token) {
        Assert.notNull(token, "token은 Null 일수 없습니다.");

        EmailVerifyJpaEntity entityData = emailJpaRepository.findByToken(token);

        return entityData;
    }

    @Override
    public void saveSendEmail(SignUpObject data) {
        String token = createToken();

        EmailVerifyJpaEntity verify = new EmailVerifyJpaEntity();
        Timestamp currentTime = Timestamp.from(Instant.now());
        verify.setMemberLoginId(data.getLoginId());
        verify.setToken(token);
        verify.setCreatedAt(currentTime);

        LocalDateTime localDateTime = currentTime.toLocalDateTime();
        LocalDateTime futureLocalDateTime = localDateTime.plus(3, ChronoUnit.YEARS);
        Timestamp futureTimestamp = Timestamp.valueOf(futureLocalDateTime);

        verify.setExpireAt(futureTimestamp);
        emailJpaRepository.save(verify);

        sendVerificationEmail(data.getEmail(),token);
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
        this.sendEmail(email, "이메일 인증", emailContent);
    }

}
