package com.sbland;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {

	@RequestMapping("/test")
	public String test() {
		return "homepage";
	}
}
