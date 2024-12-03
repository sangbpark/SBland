package com.sbland.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbland.product.bo.ProductBO;
import com.sbland.product.bo.ProductDetailBO;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductDetailCardDTO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {
	private final ProductBO productBO;
	private final ProductDetailBO productDetailBO;
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	
	@GetMapping("/product-view/{productId}")
	public String productView(
			Model model,
			@PathVariable(name="productId") Long id) {
		ProductDetailCardDTO productDetailCardDTO = productDetailBO.getProductDetailByProductId(id);
		model.addAttribute("productDetailCardDTO", productDetailCardDTO);
		return "product/productDetail";
	}
	
	@GetMapping("/product-list-view")
	public String productListView(
			Model model,
			@RequestParam("code") int code,
			@RequestParam("rightValue") int rightValue,
			@RequestParam("keyword") Optional<String> keyword
			) {
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productThumbnailCardDTOBO.getProductThumbnailCardDTOByCateogry(code, rightValue);
		model.addAttribute("productThumbnailCardDTOList", productThumbnailCardDTOList);
		return "product/productList";
	}
}
