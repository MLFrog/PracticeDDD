package com.mlfrog.practiceddd.infrastructure.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mlfrog.practiceddd.infrastructure.jpa.entity.BoardJpaEntity;

@Repository("BoardJpaRepository")
public interface BoardJpaRepository extends JpaRepository<BoardJpaEntity, Long>{
	
	List<BoardJpaEntity> findAllByOrderByBoardIdAsc(); // 게시글 전체조회
    
	BoardJpaEntity findByBoardId(long boardId);// 게시글 번호로 조회

}
