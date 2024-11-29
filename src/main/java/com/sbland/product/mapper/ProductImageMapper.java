package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.ProductImage;

@Mapper
public interface ProductImageMapper {
	public int insertProductImageList(List<ProductImage> list);
}
