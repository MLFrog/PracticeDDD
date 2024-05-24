package com.mlfrog.practiceddd.presentation.api.v1.member;

import com.mlfrog.practiceddd.application.member.MemberService;
import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignUpController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/signUp")
    public String signUpPage(){

        return "member/signupPage";
    }

    @ResponseBody
    @PostMapping(value = "/signUp/save",produces = "application/json")
    public void saveMember(@RequestBody SignUpObject data) {
        memberService.signUp(data);
    }
}
