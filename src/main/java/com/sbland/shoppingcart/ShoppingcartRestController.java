package com.sbland.shoppingcart;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.Response;
import com.sbland.shoppingcart.bo.ShoppingcartBO;
import com.sbland.shoppingcart.bo.ShoppingcartServiceBO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/shoppingcart")
@RequiredArgsConstructor
@RestController
public class ShoppingcartRestController {
	private final ShoppingcartBO shoppingcartBO;
	private final ShoppingcartServiceBO shoppingcartServiceBO;
	
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
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		Response response = shoppingcartBO.deleteShoppingcartByUserIdAndProductId(userSession.getId(), ProductId);
		return response;
	}
	
	@GetMapping("/productQuantity")
	public Response<Integer> getProductQuantity(
			@RequestParam("productId") Long productId,
			@RequestParam("productCount") int productCount,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		return shoppingcartServiceBO.shoppingcartEdit(userSession.getId(), productId, productCount);
	}
	
	@PostMapping("/insert")
	public Response addShoppingcart(
			@RequestParam("productId") Long productId,
			@RequestParam("productCount") int count,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");
		return shoppingcartBO.addShoppingcart(userSession.getId(), productId, count);		
	}
	
}
