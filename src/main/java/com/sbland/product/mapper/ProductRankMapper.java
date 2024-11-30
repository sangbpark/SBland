package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbland.product.domain.ProductRank;

@Mapper
public interface ProductRankMapper {
	public int InsertProductRankList(List<ProductRank> productRankList);
	
	public int updateProductRankByProductId(
			@Param("productId") Long id,
			@Param("totalCount") int salesVolume);
	
	public List<ProductRank> selectProductRankAll();
	
	public List<ProductRank> selectProductRanklistTop(int count);
}
