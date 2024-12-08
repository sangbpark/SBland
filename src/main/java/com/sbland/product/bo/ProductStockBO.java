package com.sbland.product.bo;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.product.domain.ProductStock;
import com.sbland.product.dto.ProductStockDTO;
import com.sbland.product.mapper.ProductStockMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductStockBO {
	private final ProductStockMapper productStockMapper;
	private final ObjectMapper objectMapper;

	public void addInitialProductStockList(List<Long> productIdList) {
		Random random = new Random();
		List<ProductStock> productStockList = productIdList
				.stream()
				.map(idValue -> {
					 ProductStock productStock = ProductStock
							.builder()
							.productId(idValue)
							.quantity(random.nextInt(20))
							.build();
					 return productStock;
				})
				.collect(Collectors.toList());
				
		productStockMapper.insertProductStockList(productStockList);
	}
	
	public void addProductStockList(List<Map<String, Object>> productStockMaps) {
		List<ProductStock> productStockList = productStockMaps
				.stream()
				.map(productStockMap -> objectMapper.convertValue(productStockMap, ProductStock.class))
				.collect(Collectors.toList());
				
		productStockMapper.insertProductStockList(productStockList);
	}
	
	public ProductStock getProductStockByProductId(Long id) {
		return productStockMapper.selectProductStockByProductId(id);
	}
	
	public List<ProductStock> getProductStockListByProductId(List<Long> productIdList) {
		return productStockMapper.selectProductStockListByProductId(productIdList);
	}
	
	public int updateProductStock(List<ProductStockDTO> productStockList) {
		try {
		return productStockMapper.updateProductStock(productStockList);
		} catch (IllegalStateException e) {
			log.info("[재고] 재고가 부족함 error:{}",e.getMessage());
			throw e;
		}
	}
}
