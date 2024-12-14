package com.sbland.product.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sbland.common.file.FileManager;
import com.sbland.product.domain.ProductImage;
import com.sbland.product.dto.EbayProductImageDTO;
import com.sbland.product.mapper.ProductImageMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductImageBO {
	private final ProductImageMapper productImageMapper;
	private final FileManager fileManager;
	
	@Transactional(propagation = Propagation.NESTED)
	public int addEbayProductImage(List<EbayProductImageDTO> ebayProductImageDTOList) {

		List<ProductImage> list = new ArrayList<>();
		for (EbayProductImageDTO epd : ebayProductImageDTOList) {
			MultipartFile file = fileManager.imageDownload(epd.getUrl());
			ProductImage productImage = ProductImage
										.builder()
										.productId(epd.getProductId())
										.url(fileManager.uploadFile(file, epd.getProductName(), "product"))
										.isThumbnail(epd.isThumbnail())
										.position(epd.getPosition())
										.build();
			list.add(productImage);
		}
		return productImageMapper.insertProductImageList(list);
	}
	
	public int addProductNotThumbnailImageList(Long productId, String name, List<MultipartFile> images) {
	
		List<ProductImage> productImagelist = IntStream.range(0, images.size())
		        .mapToObj(i -> ProductImage
		                .builder()
		                .productId(productId)
		                .url(fileManager.uploadFile(images.get(i), name, "product"))
		                .isThumbnail(false)
		                .position(i + 1)
		                .build()
		        )
		        .collect(Collectors.toList());
		return productImageMapper.insertProductImageList(productImagelist);
	}
	
	public int addProductThumbnailImage(Long productId, String name, MultipartFile imageFile) {
		return productImageMapper.insertProductImage(ProductImage
							.builder()
							.productId(productId)
							.url(fileManager.uploadFile(imageFile, name, "product"))
							.isThumbnail(true)
							.position(0)
							.build());
	}
	
	public ProductImage getProductThumbnailImageByPorductId(Long productId) {
		return productImageMapper.selectProductThumbnailImageByProductId(productId);
	}; 
	
	public List<ProductImage> getProductNotThumbnailImageByProductId(Long productId) {
		return productImageMapper.selectProductNotThumbnailImageByProductId(productId);
	};
	
	public int deleteProductImageByProductImage(ProductImage productImage) {
		fileManager.deleteFile(productImage.getUrl());
		return productImageMapper.deleteProductImageById(productImage.getId());
	}
	
	public int deleteProductNotThumbnailImageByProductIdList(List<ProductImage> productImageList) {
		productImageList
		.stream()
		.forEach(productImage -> fileManager.deleteFile(productImage.getUrl()));
		return productImageMapper.deleteProductNotThumbnailImageByProductId(productImageList.get(0).getProductId());
	};
	
	public List<Long> findProductImageIsNull() {
		return productImageMapper.findProductImageIsNull();
	}
	
	public int deleteProductImageByUrlIsNull() {
		return productImageMapper.deleteProductImageByUrlIsNull();
	}
	
	public List<ProductImage> getProductThumbnailByProductIdIn(List<Long> idList) { 
		return productImageMapper.selectProductThumbnailByproductIdIn(idList);
	}
	
	public List<ProductImage> getProductImageByProductId(Long productId) {
		return productImageMapper.selectProductImageByProductId(productId);
	}
	
}
