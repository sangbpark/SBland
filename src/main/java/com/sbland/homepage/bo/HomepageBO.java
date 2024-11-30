package com.sbland.homepage.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbland.product.bo.ProductBO;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HomepageBO {
	private final ProductBO productBO;
	
	public List<ProductThumbnailCardDTO> getBestProductTop3(){
		return null;
	}
}
