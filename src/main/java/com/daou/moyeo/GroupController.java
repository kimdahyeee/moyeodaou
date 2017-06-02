package com.daou.moyeo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GroupController {

	@RequestMapping(value = "/main")
	public String main(Model model) {
		//TODO
		return "main";
	}
	
}
