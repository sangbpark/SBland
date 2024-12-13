package com.sbland.product.bo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.product.domain.ProductImage;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Service
public class ProductAdminServiceBO {
	private final ProductBO productBO;
	private final ProductImageBO productImageBO;
	private final ProductStockBO productStockBO;

	public Response<Boolean> updateProductById(Long productId, String name, int categoryCode, String status
			, String description, int price, int quantity, MultipartFile thumbnailImage, List<MultipartFile> images) {

		productBO.updateProduct(productId, name, description, price, status, categoryCode);
				
		if (quantity != -1) {
			productStockBO.updateProductStockByProductId(productId, quantity);
		}
		
		if (thumbnailImage != null) {
			ProductImage productThumbnailImage = productImageBO.getProductThumbnailImageByPorductId(productId);
			productImageBO.deleteProductImageByProductImage(productThumbnailImage);
			productImageBO.addProductThumbnailImage(productId, name, thumbnailImage);
		}
		
		if (images != null) {
			List<ProductImage> productImage = productImageBO.getProductNotThumbnailImageByProductId(productId);
			productImageBO.deleteProductNotThumbnailImageByProductIdList(productImage);
			productImageBO.addProductNotThumbnailImageList(productId, name, images);
		}
		
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("상품 수정 성공")
				.data(true)
				.build();
	}
}
