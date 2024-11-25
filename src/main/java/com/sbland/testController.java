package com.sbland;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {

	@RequestMapping("/test")
	public String test() {
		return "homepage/homepage";
	}
	
	@RequestMapping("/test2")
	public String test2() {
		return "user/signIn";
	}
	
	@RequestMapping("/test3")
	public String test3() {
		return "user/signUp";
	}
}
