package com.mlfrog.practiceddd.infrastructure.jpa.repository;

import com.mlfrog.practiceddd.infrastructure.jpa.entity.MemberJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberJpaRepository")
public interface MemberJpaRepository extends JpaRepository<MemberJpaEntity, Long> {
    MemberJpaEntity findByLoginId(String loginId); //회원 로그인아이디로 row 조회

//    MemberJpaEntity save(MemberJEntity member); //회원가입시 회원정보 저장

    List<MemberJpaEntity> findAll(); //리스트 형태로 회원정보 전체조회

}
