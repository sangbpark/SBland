package com.sbland.homepage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sbland.homepage.bo.HomepageServiceBO;
import com.sbland.product.bo.ProductThumbnailCardDTOBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomepageController {
	private final ProductThumbnailCardDTOBO productThumbnailCardDTOBO;
	private final HomepageServiceBO homepageServiceBO;

	@GetMapping("/")
	public String Homepage(Model model) {
		List<ProductThumbnailCardDTO> bestProductThumbnailCardDTOList = productThumbnailCardDTOBO.getBestProductTop3();
		List<ProductThumbnailCardDTO> recentProductThumbnailCardDTOList = productThumbnailCardDTOBO.getRecentProductTop3();
//		List<ProductThumbnailCardDTO> mdProductThumbnailCardDTOList = productThumbnailCardDTOBO.getMdProductTop3();
//		List<Banner> bannerList = homepageServiceBO.getBannerList();
		model.addAttribute("bestProductThumbnailCardDTOList", bestProductThumbnailCardDTOList);
		model.addAttribute("recentProductThumbnailCardDTOList", recentProductThumbnailCardDTOList);
		model.addAttribute("mdProductThumbnailCardDTOList", new ArrayList<>());
		model.addAttribute("bannerList", new ArrayList<>());
		return "homepage/homepage";
	}
}
