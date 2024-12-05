package com.sbland.order;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbland.order.bo.OrderServiceBO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/order")
@RequiredArgsConstructor
@Controller
public class OrderController {
	private final OrderServiceBO orderServiceBO;

	@GetMapping("/order-view")
	public String orderView(
			@RequestParam("page") Optional<Integer> page,
			HttpSession session,
			Model model) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		model.addAttribute("orderDTOList", orderServiceBO.getOrderDTOByUserId(userSession.getId()
				, 5, page.orElse(1)).getData());
		return "user/myPage";
	}
}
