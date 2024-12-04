package com.sbland.product;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbland.common.pagination.bo.PaginationBO;
import com.sbland.common.pagination.dto.PaginationDTO;
import com.sbland.product.bo.ProductBO;
import com.sbland.product.bo.ProductDetailBO;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductDetailCardDTO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product")
@RequiredArgsConstructor
@Controller
public class ProductController {
	private final ProductBO productBO;
	private final ProductDetailBO productDetailBO;
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	private final PaginationBO paginationBO;
	
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
			@RequestParam("code") Optional<Integer> code,
			@RequestParam("rightValue") Optional<Integer> rightValue,
			@RequestParam("keyword") Optional<String> keyword,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("count") Optional<Integer> count
			) {		
			
		PaginationDTO aginationDTO = paginationBO.getProductThumbnailPaging(page.orElse(1), code.orElse(0), rightValue.orElse(0), keyword.orElse(""), count.orElse(20));
		model.addAttribute("pageList", aginationDTO.getPageDTOList());
		model.addAttribute("productThumbnailCardDTOList", aginationDTO.getProductThumbnailCardDTOList());
		model.addAttribute("nowPage", aginationDTO.getNowPageDTO());
		model.addAttribute("maxSize", aginationDTO.getMaxSize());

		return "product/productList";
	}
}
