package com.sbland.product.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.ProductStock;
import com.sbland.product.dto.ProductStockDTO;

@Mapper
public interface ProductStockMapper {
	public int insertProductStockList(List<ProductStock> productStockList);
	public ProductStock selectProductStockByProductId(Long id);
	public int updateProductStock(List<ProductStockDTO> productStockList); 
	public List<ProductStock> selectProductStockListByProductId(List<Long> productIdList);
}
