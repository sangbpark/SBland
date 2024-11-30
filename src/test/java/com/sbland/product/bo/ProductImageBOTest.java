package com.sbland.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ProductImageBOTest {
	@Autowired
	ProductImageBO productImageBO;
	@Autowired
	ProductBO productBO;

	@Transactional
	@Test
	void 프로덕트널찾기테스트() {
		List<Long> idList = new ArrayList<>();
		idList = productImageBO.findProductImageIsNull();
		int pCount = productBO.deleteProductListById(idList);
		int piCount = productImageBO.deleteProductImageByUrlIsNull();
		log.info("[테스트] idList:{} pCount:{} piCount:{}",idList, pCount, piCount);
	}
	
	@Transactional
	@Test
	void 카테고리분류테스트() {
		productBO.categoryMatch();
	}

}
