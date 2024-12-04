package com.sbland.shoppingcart;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbland.shoppingcart.bo.ShoppingcartBO;
import com.sbland.shoppingcart.bo.ShoppingcartServiceBO;
import com.sbland.shoppingcart.dto.ShoppingcartCardDTO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/shoppingcart")
@RequiredArgsConstructor
@Controller
public class ShoppingcartController {
	private final ShoppingcartBO shoppingcartBO;
	private final ShoppingcartServiceBO shoppingcartServiceBO;
	
	@GetMapping("/shoppingcart-list-view")
	public String shoppingcartView(
			Model model,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		List<ShoppingcartCardDTO> ShoppingcartCardDTOList = shoppingcartServiceBO.getShoppingcartByUserId(userSession.getId());
		model.addAttribute("ShoppingcartCardDTOList", ShoppingcartCardDTOList);
		return "shoppingcart/shoppingCart";
	}
}
