package com.mlfrog.practiceddd.presentation.api.v1.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	 @GetMapping("/AdminPage")
	    public String adminPage(){

	        return "admin/AdminPage";
	    }
	    
	    @GetMapping("/AdminLog")
	    public String adminLog(){

	        return "admin/AdminLog";
	    }
}
