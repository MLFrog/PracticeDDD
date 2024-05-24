package com.mlfrog.practiceddd.application.encrypt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Configuration
public class HashConfig{

    public HashConfig hashConfig() {
        return new HashConfig();
    }

    // 길이가 16바이트인 소금을 생성
    public String generateSalt(int length) throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // 비밀번호를 PBKDF2 해시 알고리즘으로 해싱
    public String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String algorithm = "PBKDF2WithHmacSHA512";
        int derivedKeyLength = 512;
        int iterations = 10000;

        byte[] saltBytes = Base64.getDecoder().decode(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hash);
    }
}


