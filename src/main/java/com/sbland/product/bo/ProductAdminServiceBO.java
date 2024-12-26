package com.sbland.product.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sbland.category.bo.CategoryBO;
import com.sbland.category.entity.CategoryEntity;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.ebay.bo.EbayDataBO;
import com.sbland.exrate.bo.ExRateBO;
import com.sbland.product.domain.Product;
import com.sbland.product.domain.ProductImage;
import com.sbland.product.dto.EbayProductDTO;
import com.sbland.product.dto.EbayProductImageDTO;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;



@RequiredArgsConstructor
@Service
public class ProductAdminServiceBO {
	private final ProductBO productBO;
	private final ProductImageBO productImageBO;
	private final ProductStockBO productStockBO;
	private final EbayDataBO ebayDataBO;
	private final ExRateBO exRateBO;
	private final CategoryBO categoryBO;

	@Transactional
	public Response<Boolean> updateProductById(Long productId, String name, int categoryCode, String status
			, String description, int price, int quantity, MultipartFile thumbnailImage, List<MultipartFile> images) {

		productBO.updateProduct(productId, name, description, price, status, categoryCode);
				
		if (quantity != 0) {
			productStockBO.updateProductStockByProductId(productId, quantity);
		}
		
		if (thumbnailImage != null) {
			ProductImage productThumbnailImage = productImageBO.getProductThumbnailImageByPorductId(productId);
			if (productThumbnailImage != null) {
				productImageBO.deleteProductImageByProductImage(productThumbnailImage);
			}
			productImageBO.addProductThumbnailImage(productId, name, thumbnailImage);
		}
		
		if (images != null) {
			List<ProductImage> productImage = productImageBO.getProductNotThumbnailImageByProductId(productId);
			if (!productImage.isEmpty()) {
				productImageBO.deleteProductNotThumbnailImageByProductIdList(productImage);
			}
			productImageBO.addProductNotThumbnailImageList(productId, name, images);
		}
		
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("상품 수정 성공")
				.data(true)
				.build();
	}
	
	@Transactional
	public Response<Boolean> addProductAll(String name, int categoryCode, String status
			, String description, int price, int quantity, MultipartFile thumbnailImage, List<MultipartFile> images) {

		Long productId = productBO.addProduct(name, description, price, status, categoryCode);
				

		productStockBO.addProductStock(productId, quantity);

		
		if (thumbnailImage != null) {
			productImageBO.addProductThumbnailImage(productId, name, thumbnailImage);
		}
		
		if (images != null) {
			productImageBO.addProductNotThumbnailImageList(productId, name, images);
		}
		
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("상품 추가 성공")
				.data(true)
				.build();
	}
	
	@Transactional
	public Response<Boolean> deleteProductAllById(Long productId) {

		productBO.deleteProductById(productId);
				
		productStockBO.deleteProductStock(productId);
	
		
		ProductImage productThumbnailImage = productImageBO.getProductThumbnailImageByPorductId(productId);
		if (productThumbnailImage != null) {
			productImageBO.deleteProductImageByProductImage(productThumbnailImage);
		}
		
		List<ProductImage> productImage = productImageBO.getProductNotThumbnailImageByProductId(productId);
		if (!productImage.isEmpty()) {
			productImageBO.deleteProductNotThumbnailImageByProductIdList(productImage);
		}

		
		return Response
				.<Boolean>builder()
				.code(HttpStatusCode.OK.getCodeValue())
				.message("상품 삭제 성공")
				.data(true)
				.build();
	}
	
	public void getEbayProduct(int count) {
		List<EbayProductDTO> ebayProductList = ebayDataBO.getItems("warhammer", count)
        .flatMapMany(Flux::fromIterable)
        .collectList()
        .block(); 
		for (EbayProductDTO temp : ebayProductList) {
			Long id = productBO.addProduct((String)temp.title()
					, null, exRateBO.calculateExRate(new BigDecimal ((String)temp.price().get("value"))
					, (String) temp.price().get("currency")), "판매중", null);
			List<EbayProductImageDTO> ebayProductImageDTOList = new ArrayList<>();
			EbayProductImageDTO epd = EbayProductImageDTO
									.builder()
									.productId(id)
									.isThumbnail(true)
									.position(0)
									.productName((String)temp.title())
									.url((String)temp.thumbnailImages().get(0).get("imageUrl"))
									.build();
			ebayProductImageDTOList.add(epd);
			EbayProductImageDTO epd2 = EbayProductImageDTO
									.builder()
									.productId(id)
									.isThumbnail(false)
									.position(1)
									.productName((String)temp.title())
									.url((String)temp.image().get("imageUrl"))
									.build();
			ebayProductImageDTOList.add(epd2);
			productImageBO.addEbayProductImage(ebayProductImageDTOList);
		}
	}
	
	public void categoryMatch() {
		List<Product> productList = productBO.getProductByCategoryCodeIsNull();
		Set<String> rootCategory = new HashSet<>(List.of("toy", "warhammer 40k", "book", "warhammer age of sigmar"));
		Map<String, Integer> categoryMap = categoryBO
				.getCategoryAll()
				.stream()
				.filter(categoryEntity -> rootCategory.contains(categoryEntity.getName().toLowerCase()))
				.collect(Collectors.toMap( CategoryEntity -> CategoryEntity.getName().toLowerCase(), CategoryEntity::getCode));
		for (Product product : productList) {
			int categoryCode = categoryMap
								.entrySet()
								.stream()
								.filter(category -> product.getName().toLowerCase().contains(category.getKey()) && !category.getKey().equals("space marine"))
								.map(Map.Entry::getValue)
								.findFirst()
								.orElseGet(() -> {
									 if (product.getName().toLowerCase().contains("space marine")) {
							                return categoryMap.get("space marine");
							            }
									return categoryMap.get("etc");
									});
			productBO.updateProduct(product.getId(),null,null,null,null,categoryCode);
		}
	}
}
