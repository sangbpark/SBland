package com.sbland.user;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.Response;
import com.sbland.user.bo.UserBO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserRestController {
	private final UserBO userBO;
	
	@GetMapping("/is-duplicate-id")
	public Response<Boolean> userDuplicate(
			@RequestParam("loginId") String loginId) {		
		return userBO.isDuplicateUserByLoginId(loginId);
	}
	
	@PostMapping("/sign-up")
	public Response<String> userSignUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("birthday") LocalDate birthday,
			@RequestParam("gender") String gender
			) {
		
		return userBO.addUser(loginId, password, name, email, birthday, gender);
	}
	
	@PostMapping("/sign-in")
	public Response<UserSessionDTO> userSignIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session) {
		Response<UserSessionDTO> response = userBO.getUserByUserIdAndPassword(loginId, password);
		if (response.getData() != null) {
			session.setAttribute("userSession", response.getData());
		}
		return response;
	}
	
	
}
