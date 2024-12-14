package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbland.product.domain.ProductStock;
import com.sbland.product.dto.ProductStockDTO;

@Mapper
public interface ProductStockMapper {
	public int insertProductStockList(List<ProductStock> productStockList);
	
	public ProductStock selectProductStockByProductId(Long prodcutId);
	
	public int updateProductStock(List<ProductStockDTO> productStockList); 
	
	public List<ProductStock> selectProductStockListByProductId(List<Long> productIdList);
	
	public int insertProductStock(
			@Param("productId") Long productId, 
			@Param("quantity") int quantity);
	
	public int updateProductStockByProductId(
			@Param("productId") Long productId, 
			@Param("quantity") int quantity);
	
	public int deleteProductStock(Long productId);
}
