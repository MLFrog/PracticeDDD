package com.mlfrog.practiceddd.application.board;

import java.util.List;

import com.mlfrog.practiceddd.domain.board.Board;
import com.mlfrog.practiceddd.domain.board.BoardId;

public interface BoardService {
	
	 public void save(Board board, String state);
	 
	 public List<Board> list();
	 
	 public Board board(BoardId boardId);	 

}
