package com.mlfrog.practiceddd.domain.member.roles;

public enum Roles {
	NORMAL("N", "일반회원"),
	ADMIN("A", "관리자");

	private final String code;
	private final String comment;

	Roles(String code, String comment) {
		this.code = code;
		this.comment = comment;
	}

	public static Roles valueOfCode(String code) {
		for(Roles tp : values()) {
			if(tp.code.equals(code)) {
				return tp;
			}
		}
		throw new IllegalArgumentException("코드가 일치하지 않습니다.");
	}

	public String getCode() {
		return this.code;
	}
}
