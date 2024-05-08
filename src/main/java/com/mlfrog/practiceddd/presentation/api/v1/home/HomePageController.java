package com.mlfrog.practiceddd.presentation.api.v1.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String home(){

        return "home/HomePage";
    }

}
