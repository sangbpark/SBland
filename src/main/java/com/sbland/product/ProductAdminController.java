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
import com.sbland.product.bo.ProductAdminServiceBO;
import com.sbland.product.bo.ProductBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product/admin")
@RequiredArgsConstructor
@Controller
public class ProductAdminController {
	private final PaginationBO paginationBO;
	private final ProductBO productBO;
	private final ProductAdminServiceBO productAdminServiceBO;
	
	@GetMapping("/product-update-view/{productId}")
	public String productView(
			Model model,
			@PathVariable(name="productId") Long id) {
		model.addAttribute("product",productBO.getProductById(id));
		return "admin/adminProductUpdate";
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
			
		PaginationDTO<ProductThumbnailCardDTO> paginationDTO = paginationBO.getPaging(page.orElse(1), code.orElse(0), rightValue.orElse(0), keyword.orElse(""), count.orElse(20));
		model.addAttribute("pageList", paginationDTO.getPageDTOList());
		model.addAttribute("productThumbnailCardDTOList", paginationDTO.getPaginationItems());
		model.addAttribute("nowPage", paginationDTO.getNowPageDTO());
		model.addAttribute("maxSize", paginationDTO.getMaxSize());

		return "admin/adminProductList";
	}
}
