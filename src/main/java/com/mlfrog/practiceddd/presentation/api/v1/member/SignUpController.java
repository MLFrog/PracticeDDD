package com.mlfrog.practiceddd.presentation.api.v1.member;

import com.mlfrog.practiceddd.application.emailVerify.EmailVerifyService;
import com.mlfrog.practiceddd.application.member.MemberService;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.EmailVerifyJpaEntity;
import com.mlfrog.practiceddd.presentation.api.v1.member.obj.SignUpObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignUpController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private EmailVerifyService emailVerifyService;
    @GetMapping("/signUp")
    public String signUpPage(){

        return "member/signupPage";
    }

    @ResponseBody
    @PostMapping(value = "/sign-up/save",produces = "application/json")
    public void saveMember(@RequestBody SignUpObject data) {
        memberService.signUp(data);
    }

    @ResponseBody
    @PostMapping(value = "/sign-up/idcheck",produces = "application/json")
    public boolean idDupChk(@RequestBody SignUpObject data) {
        boolean chk = memberService.comfirmDupId(data);
        return chk;
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        //email-verify테이블에서 토큰값 확인
        EmailVerifyJpaEntity entity = emailVerifyService.checkToken(token);
        //로그인아이디확인후 member테이블에서 email인증컬럼 F -> T 변경
        if(entity!=null)
            memberService.emailVerifyComplete(entity.getMemberLoginId());

        return null;
    }

}
