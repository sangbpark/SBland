package com.sbland.product.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbland.product.dto.ProductStockDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductStockServiceBO {
	private final ProductStockBO productStockBO;
	
	public boolean updateProductStock(List<ProductStockDTO> productStockDTOList) {
		int result = productStockBO.updateProductStock(productStockDTOList);
		if (result > 0 ) {
			return true;
		} else {
			log.info("[재고] 재고 업데이트 실패 productList:{}",productStockDTOList);
			return false;
		}
	}
}
