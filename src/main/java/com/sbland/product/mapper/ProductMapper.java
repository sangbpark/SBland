package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbland.product.domain.Product;

@Mapper
public interface ProductMapper {
	public Long insertProduct(Product product);
	
	public int deleteProductListById(List<Long> idList);
	
	public List<Product> selectProductByCategoryCodeIsNull();

	public int updateProductById(
								@Param("id") Long id,
								@Param("name") String name,
								@Param("description") String description,
								@Param("price") Integer price,
								@Param("status") String status,
								@Param("categoryCode") Integer categoryCode);
	
	public List<Product> selectProductByIdIn(List<Long> idList);
	
	public List<Product> selectProductByCreatedAt(int count);
	
	public Product selectProductById(Long id);
	
	public List<Product> selectProductAll();
}
