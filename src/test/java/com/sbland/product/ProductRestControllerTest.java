package com.sbland.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.common.reponse.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ProductRestControllerTest {
	@Autowired
	ProductRestController productRestController;
	
	@Test
	void 재고확인테스트() {
		Response<Integer> response = productRestController.getProductQuantity(19L, null);
		log.info("[테스트] response:{}", response);
	}

}
