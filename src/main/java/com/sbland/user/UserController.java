package com.sbland.user;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbland.common.pagination.bo.PaginationBO;
import com.sbland.common.pagination.dto.PaginationDTO;
import com.sbland.order.dto.OrderDTO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class UserController {
	private final PaginationBO paginationBO;
	
	@GetMapping("/user/user-up-view")
	public String userSignUp() {
		return "user/signUp";
	}
	
	@GetMapping("/user/user-in-view")
	public String userSignIn() {
		return "user/signIn";
	}
	
	@GetMapping("/user/protect/mypage-view")
	public String myPage(
			@RequestParam("page") Optional<Integer> page,
			Model model,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		
		PaginationDTO<OrderDTO> paginationDTO = paginationBO.getPaging(userSession.getId(), page.orElse(1), 5 );
		model.addAttribute("pageList", paginationDTO.getPageDTOList());
		model.addAttribute("orderDTOList", paginationDTO.getPaginationItems());
		model.addAttribute("nowPage", paginationDTO.getNowPageDTO());
		model.addAttribute("maxSize", paginationDTO.getMaxSize());
		return "user/myPage";
	}
	
	@GetMapping("/user/user-out")
	public String userSignOut(
			HttpSession session) {
		session.removeAttribute("userSession");
		return "redirect:/";
	}
}
