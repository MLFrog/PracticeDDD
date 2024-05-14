package com.mlfrog.practiceddd.presentation.api.v1.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/BoardList")
    public String boardList(){

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
}
