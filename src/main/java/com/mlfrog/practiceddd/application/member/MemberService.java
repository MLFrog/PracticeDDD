package com.mlfrog.practiceddd.application.member;

import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface MemberService{

    public void signUp(SignUpObject data);

    public boolean comfirmDupId(SignUpObject data);
}