package com.mlfrog.practiceddd.domain.board;

import java.util.List;

public interface BoardRepository {
	
	void insert(Board board);
	
	void update(Board board);
	
	void delete(Long boardId);
	
	List<Board>  getAllBoard();
	
	Board getOneBoardByBoardId(Long boardId);


}
