package com.sbland.product;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sbland.product.bo.ProductBO;
import com.sbland.product.bo.ProductDetailBO;
import com.sbland.product.dto.ProductDetailCardDTO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {
	private final ProductBO productBO;
	private final ProductDetailBO productDetailBO;
	
	@GetMapping("/product-view/{productId}")
	public String productView(
			Model model,
			@PathVariable(name="productId") Long id) {
		ProductDetailCardDTO productDetailCardDTO = productDetailBO.getProductDetailByProductId(id);
		model.addAttribute("productDetailCardDTO", productDetailCardDTO);
		return "product/productDetail";
	}
}
