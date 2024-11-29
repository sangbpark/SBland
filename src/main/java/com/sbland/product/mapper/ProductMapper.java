package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.Product;

@Mapper
public interface ProductMapper {
	public Long insertProduct(Product product);
	
	public int deleteProductListById(List<Long> idList);
}
