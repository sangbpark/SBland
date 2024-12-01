package com.sbland.shoppingcart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.common.reponse.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ShoppingcartRestControllerTest {
	@Autowired
	ShoppingcartRestController shoppingcartRestControllerTest;

	@Transactional
	@Test
	void 장바구니업데이트테스트() {
		Response response = shoppingcartRestControllerTest.updateShoppingcart(19L, 1, null);
		log.info("[테스트] response:{}", response);
	}
	
	@Transactional
	@Test
	void 장바구니삭제테스트() {
		Response response = shoppingcartRestControllerTest.deleteShoppingcart(19L, null);
		log.info("[테스트] response:{}", response);
	}

}
