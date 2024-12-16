package com.sbland.product.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.objectmapper.ObjectMapperFactory;
import com.sbland.homepage.bo.HomepageBO;
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
	private final HomepageBO homepageBO;
	
	public List<ProductThumbnailCardDTO> getBestProductTop3(){
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		List<Long> idList = productRankBO.getProductRankListTop(3)
										.stream()
										.map(productRank -> productRank.getProductId())
										.collect(Collectors.toList());
		if (idList.isEmpty()) return null;
		List<Product> productList = productBO.getProductByIdIn(idList);
		List<ProductStock> productStockList = productStockBO.getProductStockListByProductId(idList);
		List<ProductImage> productImageList = productImageBO.getProductThumbnailByProductIdIn(idList);
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
			    .map(product -> {
			    	ProductThumbnailCardDTO dto = camelObjectMapper.convertValue(product, ProductThumbnailCardDTO.class);
			    	return dto.toBuilder()
				    			.thumbnailImage(
				    				Optional.ofNullable(productList.indexOf(product))
					    		    .filter(index -> index != -1 && index < productImageList.size())
					    		    .map(index -> productImageList.get(index).getUrl())
					    		    .orElse(null))
				    			.quantity(productStockList.get(productList.indexOf(product)).getQuantity())
				    			.build();			    	 
			    })
			    .collect(Collectors.toList());
		return productThumbnailCardDTOList;
	}
	
	public List<ProductThumbnailCardDTO> getRecentProductTop3(){
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		List<Product> productList = productBO.getProductByCreatedAt(3);
		if (productList.isEmpty()) return null;
		List<Long> idList = productList.stream()
				.map(product -> product.getId())
				.collect(Collectors.toList());
		List<ProductStock> productStockList = productStockBO.getProductStockListByProductId(idList);
		List<ProductImage> productImageList = productImageBO.getProductThumbnailByProductIdIn(idList);
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
			    .map(product -> {
			    	ProductThumbnailCardDTO dto = camelObjectMapper.convertValue(product, ProductThumbnailCardDTO.class);
			    	return dto.toBuilder()
				    			.thumbnailImage(
				    				Optional.ofNullable(productList.indexOf(product))
					    		    .filter(index -> index != -1 && index < productImageList.size())
					    		    .map(index -> productImageList.get(index).getUrl())
					    		    .orElse(null))
				    			.quantity(productStockList.get(productList.indexOf(product)).getQuantity())
				    			.build();			    	 
			    })
			    .collect(Collectors.toList());
		return productThumbnailCardDTOList;
	}
	
	public List<ProductThumbnailCardDTO> getMdProductTop3(){
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		if (homepageBO.getHomepageMdProduct() != null) {
			List<Product> productList = productBO.getProductByIdIn(homepageBO.getHomepageMdProduct());
			if (productList.isEmpty()) return null;
			List<Long> idList = productList.stream()
					.map(product -> product.getId())
					.collect(Collectors.toList());
			List<ProductStock> productStockList = productStockBO.getProductStockListByProductId(idList);
			List<ProductImage> productImageList = productImageBO.getProductThumbnailByProductIdIn(idList);
			List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
				    .map(product -> {
				    	ProductThumbnailCardDTO dto = camelObjectMapper.convertValue(product, ProductThumbnailCardDTO.class);
				    	return dto.toBuilder()
					    			.thumbnailImage(
					    				Optional.ofNullable(productList.indexOf(product))
						    		    .filter(index -> index != -1 && index < productImageList.size())
						    		    .map(index -> productImageList.get(index).getUrl())
						    		    .orElse(null))
					    			.quantity(productStockList.get(productList.indexOf(product)).getQuantity())
					    			.build();			    	 
				    })
				    .collect(Collectors.toList());
			return productThumbnailCardDTOList;
		} else {
			return new ArrayList<>();
		}
	}
	
	public ProductThumbnailCardDTO getProductThumbnailCardDTOByProductId(Long productId) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		Product product = productBO.getProductById(productId);		
		if (product == null) return null;
		ProductImage productImage = productImageBO.getProductImageByProductId(productId)
				.stream()
				.filter(findProductImage -> findProductImage.isThumbnail())
				.findFirst()
				.orElse(ProductImage.builder().build());
		ProductStock productStock = productStockBO.getProductStockByProductId(productId);
		ProductThumbnailCardDTO ProductThumbnailCardDTO = camelObjectMapper.convertValue(product, ProductThumbnailCardDTO.class);
		return ProductThumbnailCardDTO.toBuilder().thumbnailImage(productImage.getUrl()).quantity(productStock.getQuantity()).build();
	}
	
	public List<ProductThumbnailCardDTO> getProductThumbnailCardDTOBySearch(Integer code, Integer rightValue, String keyword, int count, Integer offset) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		List<Product> productList = productBO.getProductBySearch(code, rightValue, keyword, count, offset);
		if (productList.isEmpty()) return null;
		List<Long> idList = productList.stream()
				.map(product -> product.getId())
				.collect(Collectors.toList());
		List<ProductStock> productStockList = productStockBO.getProductStockListByProductId(idList);
		List<ProductImage> productImageList = productImageBO.getProductThumbnailByProductIdIn(idList);
		List<ProductThumbnailCardDTO> productThumbnailCardDTOList = productList.stream()
			    .map(product -> {
			    	ProductThumbnailCardDTO dto = camelObjectMapper.convertValue(product, ProductThumbnailCardDTO.class);
			    	return dto.toBuilder()
				    			.thumbnailImage(
				    				Optional.ofNullable(productList.indexOf(product))
					    		    .filter(index -> index != -1 && index < productImageList.size())
					    		    .map(index -> productImageList.get(index).getUrl())
					    		    .orElse(null))
				    			.quantity(productStockList.get(productList.indexOf(product)).getQuantity())
				    			.build();			    	 
			    })
			    .collect(Collectors.toList());
		return productThumbnailCardDTOList;
	}
	
	public int getProductThumbnailCardDTOSizeBySearch(Integer code, Integer rightValue, String keyword) {
		return productBO.getProductSizeBySearch(code, rightValue, keyword);
	}
	
}
