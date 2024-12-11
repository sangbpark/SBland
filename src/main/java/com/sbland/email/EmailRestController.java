package com.sbland.email;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.Response;
import com.sbland.email.bo.EmailServiceBO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/email")
@RequiredArgsConstructor
@RestController
public class EmailRestController {
	private final EmailServiceBO emailServiceBO;
	
	@GetMapping("/verify-send")
	public Response<Boolean> verifyEmailSend(
			@RequestParam("email") String email) {
		
		return emailServiceBO.sendVerifyEmail(email);
	}
	
	@GetMapping("/verify")
	public Response<Boolean> verifyEmail (
			@RequestParam("salt") String salt,
			@RequestParam("email") String email){
				
		return emailServiceBO.verifyEmail(salt, email);
	};
	
}
