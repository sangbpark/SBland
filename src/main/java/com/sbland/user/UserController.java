package com.sbland.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class UserController {
	
	@GetMapping("/user/user-up-view")
	public String userSignUp() {
		return "user/signUp";
	}
	
	@GetMapping("/user/user-in-view")
	public String userSignIn() {
		return "user/signIn";
	}
	
	@GetMapping("/user/mypage-view")
	public String myPage() {
		return "user/myPage";
	}
	
	@GetMapping("/user/user-out")
	public String userSignOut(
			HttpSession session) {
		session.removeAttribute("userSession");
		return "redirect:/";
	}
}
