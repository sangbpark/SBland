package com.sbland.product.bo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sbland.category.bo.CategoryBO;
import com.sbland.category.entity.CategoryEntity;
import com.sbland.product.domain.Product;
import com.sbland.product.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class ProductBO {
	private final ProductMapper productMapper;
	private final CategoryBO  categoryBO;
	private final ProductRankBO productRankBO;
	

	public Long addProduct(String name, String description, int price, String status, Integer categoryCode) {
		Product product = Product
				.builder()
				.name(name)
				.description(description)
				.price(price)
				.status(status)
				.categoryCode(categoryCode)
				.build();
		productMapper.insertProduct(product);
		Long id = product.getId();
		return id;
	}
	
	public int deleteProductListById(List<Long> idList) {
		return productMapper.deleteProductListById(idList);
	}
	
	public int updateProduct (Long id, String name, String description, Integer price, String status, Integer categoryCode ) {
		return productMapper.updateProductById(id, name, description, price, status, categoryCode);
	}
	
	public void categoryMatch() {
		List<Product> productList = productMapper.findProductByCategoryCodeIsNull();
		Map<String, Integer> categoryMap = categoryBO
				.getCategoryAll()
				.stream()
				.filter(categoryEntity -> !categoryEntity.getName().equalsIgnoreCase("Toy")
									   && !categoryEntity.getName().equalsIgnoreCase("warhammer 40k")
									   && !categoryEntity.getName().equalsIgnoreCase("book")
									   && !categoryEntity.getName().equalsIgnoreCase("warhammer age of sigmar")
									   && !categoryEntity.getName().equalsIgnoreCase("Toy")
									   )
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
			updateProduct(product.getId(),null,null,null,null,categoryCode);
		}
	}
	
	public List<Product> getProductByIdIn(List<Long> idList) {
		return productMapper.selectProductByIdIn(idList);
	}
	
	public List<Product> getProductByCreatedAt(int count) {
		return productMapper.selectProductByCreatedAt(count);
	}
	
	public void test() {
		List<Long> idList = productMapper.findProductByCategoryCodeIsNull()
				.stream()
				.map(product -> product.getId())
				.collect(Collectors.toList());
		productRankBO.addProductRankList(idList);
	}
}
