package com.sbland.product.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sbland.product.domain.Product;
import com.sbland.product.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ProductBO {
	private final ProductMapper productMapper;
	

	public Long addProduct(String name, String description, int price, String status, Integer categoryCode) {
		Product product = Product
				.builder()
				.name(name)
				.description(description)
				.price(price)
				.status(status)
				.category_code(price)
				.build();
		return productMapper.insertProduct(product);
	}
}
