package com.mlfrog.practiceddd.domain.common;

public interface DomainConverter<A, B> {
	B convert(A a);
}