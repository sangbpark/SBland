package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.ProductImage;

@Mapper
public interface ProductImageMapper {
	public int insertProductImageList(List<ProductImage> list);
	public int deleteProductImageById(Long id);
	public List<Long> findProductImageIsNull();
	public int deleteProductImageByUrlIsNull();
	public List<ProductImage> selectProductThumbnailByproductIdIn(List<Long> idList);
}
