package com.mlfrog.practiceddd.application.emailVerify;

public interface EmailVerifyService {
    public void sendEmail(String to, String subject, String content);
}
