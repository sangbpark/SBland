package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.ProductRank;

@Mapper
public interface ProductRankMapper {
	public int InsertProductRankList(List<ProductRank> productRankList);
}
