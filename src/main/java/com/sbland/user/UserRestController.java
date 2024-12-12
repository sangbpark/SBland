package com.sbland.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.HttpStatusCode;
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
		Response<UserSessionDTO> response = userBO.getUserByUserLoginIdAndPassword(loginId, password);
		if (response.getData() != null) {
			session.setAttribute("userSession", response.getData());
		}
		return response;
	}
	
	@GetMapping("/find-id")
	public Response<List<String>> userFindId(
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		return userBO.getFindUserByNameAndEmail(name,email);
	}
	
	@GetMapping("/find-password")
	public Response<Boolean> userFindPassword(
			@RequestParam("name") String name,
			@RequestParam("loginId") String loginId) {
		
		return userBO.changePsswordByUserNameAndUserLoginId(name,loginId);
	}
	
	@PostMapping("/protect/verify")
	public Response<Boolean> userInfoVerify(
			@RequestParam("password") String password,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		return userBO.getUserByUseIdAndPassword(userSession.getId(), password);
	}

	@PostMapping("/protect/update")
	public Response<Boolean> userUpdate(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("birthday") LocalDate birthday,
			@RequestParam("gender") String gender,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		return userBO.updateUser(userSession.getId(), loginId, password, name, email, birthday, gender);
	}
	
	@GetMapping("/check")
	public Response<Boolean> userCheck(
			HttpSession session) {
		UserSessionDTO user = (UserSessionDTO)session.getAttribute("userSession");
		if (user == null) {
			return Response
					.<Boolean>builder()
					.code(HttpStatusCode.FAIL.getCodeValue())
					.message("로그인을 확인해주세요")
					.data(false)
					.build();
		}
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("로그인중")
				.data(true)
				.build();
	}
	
	
}
