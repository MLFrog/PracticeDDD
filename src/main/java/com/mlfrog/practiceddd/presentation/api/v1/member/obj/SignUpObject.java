package com.mlfrog.practiceddd.presentation.api.v1.member.obj;

import com.mlfrog.practiceddd.domain.member.roles.Roles;
import lombok.Data;

@Data
public class SignUpObject {
	private String loginId;
	private String password;
	private String email;
	private String phoneNumber;
	private String nickname;
	private String realname;
	private Roles roles;
}
