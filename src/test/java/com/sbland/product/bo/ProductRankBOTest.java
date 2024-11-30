package com.sbland.product.bo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProductRankBOTest {
	@Autowired
	ProductRankBO productRankBO;
	@Autowired
	ProductBO productBO;
	
	@Transactional
	@Test
	void 상품랭크테스트() {
		List<Long> idList = new ArrayList<>();
		idList.add(1L);
		idList.add(2L);
		idList.add(3L);
		productRankBO.addProductRankList(idList);
	}
	
	@Test
	void 상품랭크테스트2() {
		productBO.test();
	}

}
