package com.sbland.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.product.domain.ProductImage;

@Mapper
public interface ProductImageMapper {
	public int insertProductImageList(List<ProductImage> list);
	
	public int insertProductImage(ProductImage productImage);
	
	public int deleteProductImageById(Long id);
	
	public List<Long> findProductImageIsNull();
	
	public int deleteProductImageByUrlIsNull();
	
	public List<ProductImage> selectProductThumbnailByproductIdIn(List<Long> idList);
	
	public List<ProductImage> selectProductImageByProductId(Long productId);
	
	public ProductImage selectProductThumbnailImageByProductId(Long productId);
	
	public List<ProductImage> selectProductNotThumbnailImageByProductId(Long productId);
	
	public int deleteProductNotThumbnailImageByProductId(Long productId);
}
