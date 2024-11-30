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
		List<ProductThumbnailCardDTO> bestProductThumbnailCardDTOList = homepageBO.getBestProductTop3();
		List<ProductThumbnailCardDTO> recentProductThumbnailCardDTOList = homepageBO.getRecentProductTop3();
		model.addAttribute("bestProductThumbnailCardDTOList", bestProductThumbnailCardDTOList);
		model.addAttribute("recentProductThumbnailCardDTOList", recentProductThumbnailCardDTOList);
		return "homepage/homepage";
	}
}
