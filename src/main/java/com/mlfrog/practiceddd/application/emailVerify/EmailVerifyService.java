package com.mlfrog.practiceddd.application.emailVerify;

import com.mlfrog.practiceddd.infrastructure.jpa.entity.EmailVerifyJpaEntity;
import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;

public interface EmailVerifyService {
    public void sendEmail(String to, String subject, String content);

    public EmailVerifyJpaEntity checkToken(String token);

    public void saveSendEmail(SignUpObject data);
}
