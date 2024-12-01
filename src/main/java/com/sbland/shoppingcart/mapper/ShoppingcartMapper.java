package com.sbland.shoppingcart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbland.shoppingcart.domain.Shoppingcart;

@Mapper
public interface ShoppingcartMapper {
	public int insertShoppingcart(
			@Param("userId") Long userId, 
			@Param("productId") Long productId, 
			@Param("productCount") int productCount);
	
	public List<Shoppingcart> selectShoppingcartByUserId(Long id);
	
	public Shoppingcart selectShoppingcartByUserIdAndProductId(
			@Param("userId") Long userId,
			@Param("productId") Long productId);
	
	public int updateShoppingcart(
			@Param("userId") Long userId,
			@Param("productId") Long productId,
			@Param("count") int count);
	
	public int deleteShoppingcartByUserIdAndProductId(
			@Param("userId") Long userId,
			@Param("productId") Long productId);

}
