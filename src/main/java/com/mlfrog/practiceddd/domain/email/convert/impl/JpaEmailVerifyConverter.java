package com.mlfrog.practiceddd.domain.email.convert.impl;

import com.mlfrog.practiceddd.domain.email.Email;
import com.mlfrog.practiceddd.domain.email.EmailFactory;
import com.mlfrog.practiceddd.domain.email.convert.EmailVerifyConverter;
import com.mlfrog.practiceddd.infrastructure.jpa.entity.EmailVerifyJpaEntity;

public class JpaEmailVerifyConverter implements EmailVerifyConverter<EmailVerifyJpaEntity> {
	
	private final EmailFactory factory = new EmailFactory();


	@Override
	public Email convert(EmailVerifyJpaEntity emailVerifyJpaEntity) {
		return null;
	}
}
