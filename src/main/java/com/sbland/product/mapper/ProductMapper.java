package com.sbland.product.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.Product;

@Mapper
public interface ProductMapper {
	public Long insertProduct(Product product);
}
