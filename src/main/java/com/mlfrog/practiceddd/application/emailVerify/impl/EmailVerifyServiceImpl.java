package com.mlfrog.practiceddd.application.emailVerify.impl;

import com.mlfrog.practiceddd.application.emailVerify.EmailVerifyService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailVerifyServiceImpl implements EmailVerifyService {

    @Autowired
    private JavaMailSender mailSender;

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
}
