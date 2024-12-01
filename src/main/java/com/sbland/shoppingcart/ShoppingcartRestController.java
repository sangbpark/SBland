package com.sbland.shoppingcart;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.Response;
import com.sbland.shoppingcart.bo.ShoppingcartBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/shoppingcart")
@RequiredArgsConstructor
@RestController
public class ShoppingcartRestController {
	private final ShoppingcartBO shoppingcartBO;
	
	@PostMapping("/update")
	public Response updateShoppingcart(
			@RequestParam("productId") Long ProductId,
			@RequestParam("productCount") int count,
			HttpSession session) {
		Response response = shoppingcartBO.updateShoppingcartByUserIdAndProductId(1L, ProductId, count);
		
		return response;
	}
	
	@DeleteMapping("/delete")
	public Response deleteShoppingcart(
			@RequestParam("productId") Long ProductId,
			HttpSession session) {
		Response response = shoppingcartBO.deleteShoppingcartByUserIdAndProductId(1L, ProductId);
		return response;
	}
}
