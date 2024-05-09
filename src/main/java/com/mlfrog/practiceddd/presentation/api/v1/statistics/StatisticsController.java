package com.mlfrog.practiceddd.presentation.api.v1.statistics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    @GetMapping("Statistics")
    public String statistics(){

        return "statistics/StatisticsPage";
    }

}
