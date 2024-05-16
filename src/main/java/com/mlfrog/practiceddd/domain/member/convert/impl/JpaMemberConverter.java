package com.mlfrog.practiceddd.domain.member.convert.impl;

import com.mlfrog.practiceddd.domain.member.Member;
import com.mlfrog.practiceddd.domain.member.MemberFactory;
import com.mlfrog.practiceddd.domain.member.convert.MemberConverter;
import com.mlfrog.practiceddd.domain.member.roles.Roles;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;

import java.time.Instant;
import java.util.Optional;

public class JpaMemberConverter implements MemberConverter<MemberJpaEntity> {
	
	private final MemberFactory factory = new MemberFactory();

	@Override
	public Member convert(MemberJpaEntity entity) {
		Member obj = this.factory.getInstance();
		obj.setRowId(entity.getRowId());
		obj.setLoginId(Optional.ofNullable(entity.getLoginId()).orElse(null));
		obj.setPassword(Optional.ofNullable(entity.getPassword()).orElse(null));
		obj.setEmail(Optional.ofNullable(entity.getEmail()).orElse(null));
		obj.setPhoneNumber(Optional.ofNullable(entity.getPhoneNumber()).orElse(null));
		obj.setNickname(Optional.ofNullable(entity.getNickname()).orElse(null));
		obj.setRealname(Optional.ofNullable(entity.getRealname()).orElse(null));
		obj.setEmailVerifiedTp(Optional.ofNullable(entity.getEmailVerifiedTp()).orElse(false));
		obj.setRoles(Optional.ofNullable(Roles.valueOfCode(entity.getRoles())).orElse(null));
		obj.setCreatedAt(Instant.now());
		obj.setUpdatedAt(Instant.now());

		return obj;
	}
}
