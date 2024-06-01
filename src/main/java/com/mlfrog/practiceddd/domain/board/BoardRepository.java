package com.mlfrog.practiceddd.domain.board;

import java.util.List;

public interface BoardRepository {
	
	void save(Board board);
	
	List<Board> findAll();
	
	Board findByBoardId(Long boardId);


}
