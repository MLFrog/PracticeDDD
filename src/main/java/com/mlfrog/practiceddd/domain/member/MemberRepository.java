package com.mlfrog.practiceddd.domain.member;

import java.util.List;

public interface MemberRepository {
	void save(Member member);
	Member getOneMemberByRowId(Long id);

	Member getOneMemberByMemberId(String loginId);
	List<Member> getAllMember();
	Boolean isMember(String loginId);
	String getMemberIdByEmail(String email);
	Boolean deleteMember(String loginId);
}
