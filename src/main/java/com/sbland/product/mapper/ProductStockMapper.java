package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.ProductStock;

@Mapper
public interface ProductStockMapper {
	public int insertProductStockList(List<ProductStock> productStockList);
	public ProductStock selectProductStockByProductId(Long id);
}
