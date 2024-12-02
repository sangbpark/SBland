package com.sbland.shoppingcart;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.shoppingcart.bo.ShoppingcartBO;
import com.sbland.shoppingcart.bo.ShoppingcartServiceBO;

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
		Response response = shoppingcartBO.deleteShoppingcartByUserIdAndProductId(1L, ProductId);
		return response;
	}
	
	@GetMapping("/productQuantity")
	public Response<Integer> getProductQuantity(
			@RequestParam("productId") Long productId,
			@RequestParam("productCount") int productCount,
			HttpSession session) {
		
		return shoppingcartServiceBO.shoppingcartEdit(1L, productId, productCount);
	}
	
}
