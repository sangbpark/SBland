package com.sbland.product.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProductStockBOTest {
	@Autowired
	ProductStockBO productStockBO;
	@Autowired
	ProductBO productBO;

	@Transactional
	@Test
	void 재고파싱테스트() {
		List<Map<String,Object>> productList = new ArrayList<>();
		
		Map<String,Object> product = new HashMap<>();
		product.put("productId", 1L);
		product.put("quantity", 10);
		productList.add(product);
		productStockBO.addProductStockList(productList);
	}
	
	@Transactional
	@Test
	void 재고이니셜라이징테스트() {
		productStockBO.addInitialProductStockList(productBO.getProductIdAll());
	}

}
