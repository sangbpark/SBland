package com.sbland.product;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sbland.common.reponse.Response;
import com.sbland.product.bo.ProductAdminServiceBO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/product")
@RequiredArgsConstructor
@RestController
public class ProductAdminRestController {
	private final ProductAdminServiceBO productAdminServiceBO;
	
	@PostMapping("/update")
	public Response<Boolean> updateProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("name") String name,
            @RequestParam("categoryCode") int categoryCode,
            @RequestParam("status") String status,
            @RequestParam("description") Optional<String> description,
            @RequestParam("price") int price,
            @RequestParam("quantity") Optional<Integer> quantity,
            @RequestParam(value = "thumbnailImage", required = false) MultipartFile thumbnailImage, 
            @RequestParam(value = "images", required = false) List<MultipartFile> images 
			) {
		
		return productAdminServiceBO.updateProductById(productId, name, categoryCode
				, status, description.orElse(""), price
				, quantity.orElse(0), thumbnailImage, images);
	}
	
	@PostMapping("/insert")
	public Response<Boolean> addProduct(
            @RequestParam("name") String name,
            @RequestParam("categoryCode") int categoryCode,
            @RequestParam("status") String status,
            @RequestParam("description") Optional<String> description,
            @RequestParam("price") int price,
            @RequestParam("quantity") Optional<Integer> quantity,
            @RequestParam(value = "thumbnailImage", required = false) MultipartFile thumbnailImage, 
            @RequestParam(value = "images", required = false) List<MultipartFile> images 
			) {
		
		return productAdminServiceBO.addProductAll(name, categoryCode
				, status, description.orElse(""), price
				, quantity.orElse(0), thumbnailImage, images);
	}
	
	@PostMapping("/delete")
	public Response<Boolean> deleteProduct(
			@RequestParam("productId") Long productId) {
		
		return productAdminServiceBO.deleteProductAllById(productId);
	}

}
