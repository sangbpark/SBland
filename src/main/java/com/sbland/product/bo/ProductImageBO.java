package com.sbland.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbland.common.file.FileManager;
import com.sbland.product.domain.EbayProductImageDTO;
import com.sbland.product.domain.ProductImage;
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
										.product_id(epd.getProduct_id())
										.url(fileManager.uploadFile(file, epd.getProduct_name(), "product"))
										.is_thumbnail(epd.getIs_thumbnail())
										.position(epd.getPosition())
										.build();
			list.add(productImage);
		}
		return productImageMapper.insertProductImageList(list);
	}
}
