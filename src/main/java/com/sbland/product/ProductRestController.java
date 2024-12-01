package com.sbland.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.product.bo.ProductStockBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/product")
@RequiredArgsConstructor
@RestController
public class ProductRestController {
	private final ProductStockBO productStockBO;
	
	@GetMapping("/productQuantity")
	public Response<Integer> getProductQuantity(
			@RequestParam("productId") Long productId,
			HttpSession session) {
		
		Integer quantity = productStockBO.getProductStockByProductId(productId).getQuantity();
		Response<Integer> response = Response
				.<Integer>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("성공")
				.data(quantity)
				.build();
		return response;
	}
}
