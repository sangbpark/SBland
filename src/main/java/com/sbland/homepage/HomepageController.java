package com.sbland.homepage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sbland.homepage.bo.HomepageBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomepageController {
	private final HomepageBO homepageBO;

	@GetMapping("/")
	public String Homepage(Model model) {
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = homepageBO.getBestProductTop3();
		model.addAttribute("productThumbnailCardDTOList", productThumbnailCardDTOList);
		return "homepage/homepage";
	}
}
