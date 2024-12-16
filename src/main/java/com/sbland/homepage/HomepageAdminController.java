package com.sbland.homepage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomepageAdminController {
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;

	@GetMapping("/admin")
	public String Homepage(Model model) {
		List<ProductThumbnailCardDTO> bestProductThumbnailCardDTOList = productThumbnailCardDTOBO.getBestProductTop3();
		List<ProductThumbnailCardDTO> recentProductThumbnailCardDTOList = productThumbnailCardDTOBO.getRecentProductTop3();
		model.addAttribute("bestProductThumbnailCardDTOList", bestProductThumbnailCardDTOList);
		model.addAttribute("recentProductThumbnailCardDTOList", recentProductThumbnailCardDTOList);
		return "admin/adminHomepage";
	}
	
	@GetMapping("/admin/hompage-update-view")
	public String productBannerUpdateView() {
		return "admin/adminHomePageUdate";
	}

}
