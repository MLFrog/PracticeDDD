package com.mlfrog.practiceddd.presentation.api.v1.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mlfrog.practiceddd.application.board.BoardService;
import com.mlfrog.practiceddd.domain.board.Board;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;
	 
    @GetMapping("/BoardList")
    public String boardList(Model model){
    	
    	List<Board> boardServiceList = boardService.list();
    	List<Board> boardList = boardServiceList.stream()
				                .map(boardTitle -> {
				                	boardTitle.setTitle(truncateText(boardTitle.getTitle(), 15));
				                    return boardTitle;
                })
                .collect(Collectors.toList());
            model.addAttribute("boardList", boardList);
    	
        return "board/BoardList";
    }
    
    @GetMapping("/BoardPage")
    public String boardPage(){

        return "board/BoardPage";
    }

    @GetMapping("/BoardWrite")
    public String boardWrite(){

        return "board/BoardWrite";
    }
    
    @PostMapping("/write")
    public String writedo(Board board, String state) {

    	System.out.println("board : " + board + ", state : " + state );
        //boardService.save(board, state);

        return "board/BoardList";
    }
    
    private String truncateText(String title, int length) {
        if (title.length() <= length) {
            return title;
        }
        return title.substring(0, length) + "...";
    }
    
}
