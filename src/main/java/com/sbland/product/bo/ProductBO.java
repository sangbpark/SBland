package com.sbland.product.bo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	public int updateProduct (Long id, String name, String description, Integer price, String status, Integer categoryCode ) {
		return productMapper.updateProductById(id, name, description, price, status, categoryCode);
	}
	
	public List<Product> getProductByCategoryCodeIsNull() {
		return productMapper.selectProductByCategoryCodeIsNull();
	}
	
	public List<Product> getProductByIdIn(List<Long> idList) {
		return productMapper.selectProductByIdIn(idList);
	}
	
	public List<Product> getProductByCreatedAt(int count) {
		return productMapper.selectProductByCreatedAt(count);
	}
	
	public List<Product> getProductBySearch(Integer code, Integer rightValue, String keyword, int count, Integer offset) {
		return productMapper.selectProductBySearch(code, rightValue, keyword, count, offset);
	}
	
	public int getProductSizeBySearch(Integer code, Integer rightValue, String keyword) {
		return productMapper.selectProductSizeBySearch(code, rightValue, keyword);
	}
	
	public Product getProductById(Long id) {
		return productMapper.selectProductById(id);
	}
	
	public void test() {
		List<Long> idList = productMapper.selectProductByCategoryCodeIsNull()
				.stream()
				.map(product -> product.getId())
				.collect(Collectors.toList());
		productRankBO.addProductRankList(idList);
	}
	
	public List<Long> getProductIdAll() {
		List<Long> productId = productMapper.selectProductAll()
				.stream()
				.map(product -> product.getId())
				.collect(Collectors.toList());
		return productId;
	}
	
	public int deleteProductById(Long id) {
		return productMapper.deleteProductById(id);
	}
}
