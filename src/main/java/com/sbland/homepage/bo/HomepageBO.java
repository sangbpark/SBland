package com.sbland.homepage.bo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.product.bo.ProductBO;
import com.sbland.product.bo.ProductImageBO;
import com.sbland.product.bo.ProductRankBO;
import com.sbland.product.domain.Product;
import com.sbland.product.domain.ProductImage;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HomepageBO {
	private final ProductBO productBO;
	private final ProductRankBO productRankBO;
	private final ProductImageBO productImageBO;
	private final ObjectMapper objectMapper;
	
	public List<ProductThumbnailCardDTO> getBestProductTop3(){
		List<Long> idList = productRankBO.getProductRankListTop(3)
										.stream()
										.map(productRank -> productRank.getProductId())
										.collect(Collectors.toList());
		List<Product> productList = productBO.getProductByIdIn(idList);
		List<ProductImage> productImageList = productImageBO.getProductThumbnailByproductIdIn(idList);
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
			    .map(product -> {
			    	ProductThumbnailCardDTO dto = objectMapper.convertValue(product, ProductThumbnailCardDTO.class);
			    	return dto.toBuilder().ThumbnailImage(productImageList.get(productList.indexOf(product)).getUrl()).build();			    	 
			    })
			    .collect(Collectors.toList());
		return productThumbnailCardDTOList;
	}
}
