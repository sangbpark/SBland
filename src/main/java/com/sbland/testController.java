package com.sbland;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbland.ebay.EbayDataService;

@Controller
public class testController {
	@Autowired
	private EbayDataService es;

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
	
	@RequestMapping("/test4")
	public String test4() {
		return "product/productList";
	}
	
	@RequestMapping("/test5")
	public String test5() {
		return "user/shoppingCart";
	}
	
	@RequestMapping("/test6")
	public String test6() {
		return "user/myPage";
	}
	
	@ResponseBody
	@RequestMapping("/test7")
	public List<Map<String, Object>> test7() {
		List<Map<String, Object>> result = es.getItemsBySeller("cataclysmcollectables");
		
		return result;
	}
}
