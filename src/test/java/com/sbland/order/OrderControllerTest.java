package com.sbland.order;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sbland.common.reponse.Response;
import com.sbland.order.bo.OrderServiceBO;
import com.sbland.order.dto.OrderDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class OrderControllerTest {
	@Autowired
	OrderController oderController;
	@Autowired
	OrderServiceBO orderServiceBO;

	@Test
	void 주문명세서테스트() {
		Response<List<OrderDTO>> response =orderServiceBO.getOrderDTOByUserId(4L, 5, 0);
		log.info("[테스트] response:{}", response);
	}

}
