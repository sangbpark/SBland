package com.sbland.order.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.common.reponse.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class OrderBOTest {
	@Autowired
	OrderBO orderBO;
	
//	@Transactional
	@Test
	void 주문생성테스트() {
		Response<Long> result = orderBO.addOrder(4L, 0, 0, "pending", "홈");
		log.info("[주문생성테스트] result:{}",result);
	}

}
