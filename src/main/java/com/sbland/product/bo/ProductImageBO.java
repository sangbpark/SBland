package com.sbland.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
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
	
	public int addProductImage(List<EbayProductImageDTO> ebayProductImageDTOList) {

		List<ProductImage> list = new ArrayList<>();
		for (EbayProductImageDTO epd : ebayProductImageDTOList) {
			MultipartFile file = fileManager.imageDownload(epd.getUrl());
			ProductImage productImage = ProductImage
										.builder()
										.productId(epd.getProductId())
										.url(fileManager.uploadFile(file, epd.getProductName(), "product"))
										.isThumbnail(epd.getIsThumbnail())
										.position(epd.getPosition())
										.build();
			list.add(productImage);
		}
		return productImageMapper.insertProductImageList(list);
	}
	
	public int deleteProductImageById(Long id) {
		return productImageMapper.deleteProductImageById(id);
	}
	
	public List<Long> findProductImageIsNull() {
		return productImageMapper.findProductImageIsNull();
	}
	
	public int deleteProductImageByUrlIsNull() {
		return productImageMapper.deleteProductImageByUrlIsNull();
	}
	
	public List<ProductImage> getProductThumbnailByproductIdIn(List<Long> idList) { 
		return productImageMapper.selectProductThumbnailByproductIdIn(idList);
	}
}
