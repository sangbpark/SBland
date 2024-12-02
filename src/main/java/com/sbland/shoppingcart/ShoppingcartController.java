package com.sbland.shoppingcart;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbland.shoppingcart.bo.ShoppingcartBO;
import com.sbland.shoppingcart.bo.ShoppingcartServiceBO;
import com.sbland.shoppingcart.dto.ShoppingcartCardDTO;

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
			HttpSession session,
			@RequestParam("userId") Long userId) {
		List<ShoppingcartCardDTO> ShoppingcartCardDTOList = shoppingcartServiceBO.getShoppingcartByUserId(userId);
		model.addAttribute("ShoppingcartCardDTOList", ShoppingcartCardDTOList);
		return "shoppingcart/shoppingCart";
	}
}
