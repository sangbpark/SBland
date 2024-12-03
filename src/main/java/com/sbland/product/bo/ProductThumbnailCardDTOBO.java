package com.sbland.product.bo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.product.domain.Product;
import com.sbland.product.domain.ProductImage;
import com.sbland.product.domain.ProductStock;
import com.sbland.product.dto.ProductThumbnailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductThumbnailCardDTOBO {
	private final ProductBO productBO;
	private final ProductRankBO productRankBO;
	private final ProductImageBO productImageBO;
	private final ProductStockBO productStockBO;
	private final ObjectMapper objectMapper;
	
	public List<ProductThumbnailCardDTO> getBestProductTop3(){
		List<Long> idList = productRankBO.getProductRankListTop(3)
										.stream()
										.map(productRank -> productRank.getProductId())
										.collect(Collectors.toList());
		if (idList.isEmpty()) return null;
		List<Product> productList = productBO.getProductByIdIn(idList);
		List<ProductImage> productImageList = productImageBO.getProductThumbnailByproductIdIn(idList);
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
			    .map(product -> {
			    	ProductThumbnailCardDTO dto = objectMapper.convertValue(product, ProductThumbnailCardDTO.class);
			    	return dto.toBuilder().thumbnailImage(productImageList.get(productList.indexOf(product)).getUrl()).build();			    	 
			    })
			    .collect(Collectors.toList());
		return productThumbnailCardDTOList;
	}
	
	public List<ProductThumbnailCardDTO> getRecentProductTop3(){
		List<Product> productList = productBO.getProductByCreatedAt(3);
		if (productList.isEmpty()) return null;
		List<Long> idList = productList.stream()
				.map(product -> product.getId())
				.collect(Collectors.toList());
		List<ProductImage> productImageList = productImageBO.getProductThumbnailByproductIdIn(idList);
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
			    .map(product -> {
			    	ProductThumbnailCardDTO dto = objectMapper.convertValue(product, ProductThumbnailCardDTO.class);
			    	return dto.toBuilder().thumbnailImage(productImageList.get(productList.indexOf(product)).getUrl()).build();			    	 
			    })
			    .collect(Collectors.toList());
		return productThumbnailCardDTOList;
	}
	
	public ProductThumbnailCardDTO getProductThumbnailCardDTOByProductId(Long productId) {
		Product product = productBO.getProductById(productId);
		if (product == null) return null;
		ProductImage productImage = productImageBO.getProductImageByProductId(productId)
				.stream()
				.filter(findProductImage -> findProductImage.isThumbnail())
				.findFirst()
				.orElse(null);
		ProductStock productStock = productStockBO.getProductStockByProductId(productId);
		ProductThumbnailCardDTO ProductThumbnailCardDTO = objectMapper.convertValue(product, ProductThumbnailCardDTO.class);
		return ProductThumbnailCardDTO.toBuilder().thumbnailImage(productImage.getUrl()).quantity(productStock.getQuantity()).build();
	}
	
	public List<ProductThumbnailCardDTO> getProductThumbnailCardDTOByCateogry(int code, int rightValue) {
		List<Product> productList = productBO.getProductByCategoryCode(code, rightValue);
		if (productList.isEmpty()) return null;
		List<Long> idList = productList.stream()
				.map(product -> product.getId())
				.collect(Collectors.toList());
		List<ProductImage> productImageList = productImageBO.getProductThumbnailByproductIdIn(idList);
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
			    .map(product -> {
			    	ProductThumbnailCardDTO dto = objectMapper.convertValue(product, ProductThumbnailCardDTO.class);
			    	return dto.toBuilder().thumbnailImage(productImageList.get(productList.indexOf(product)).getUrl()).build();			    	 
			    })
			    .collect(Collectors.toList());
		return productThumbnailCardDTOList;
	}
	
}
