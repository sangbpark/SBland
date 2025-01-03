package com.sbland.product.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.keys.KeysGenerator;
import com.sbland.common.objectmapper.ObjectMapperFactory;
import com.sbland.product.domain.Product;
import com.sbland.product.domain.ProductImage;
import com.sbland.product.domain.ProductStock;
import com.sbland.product.dto.ProductDetailCardDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductDetailBO {
	private final ProductBO productBO;
	private final ProductImageBO productImageBO;
	private final ProductStockBO productStockBO;
	
	public ProductDetailCardDTO getProductDetailByProductId(Long id) {
		ObjectMapper camelObjectMapper = new ObjectMapperFactory().getCamelObjectMapper();
		Product product = productBO.getProductById(id);
		List<ProductImage> productImageList = productImageBO.getProductImageByProductId(id);
		ProductStock productStock = productStockBO.getProductStockByProductId(id);
		ProductDetailCardDTO productDetailCardDTO = camelObjectMapper.convertValue(product, ProductDetailCardDTO.class);
		return productDetailCardDTO.toBuilder().url(productImageList).quantity(productStock.getQuantity()).build();
	}
	
	public String getMerchantUid() {
		return new KeysGenerator().getMerchantUid();
	}
}
