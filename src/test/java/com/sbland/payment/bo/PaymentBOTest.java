package com.sbland.payment.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class PaymentBOTest {
	@Autowired
	PaymentBO paymentBO;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void 맵퍼테스트() {
		log.info("[테스트] paymentApi:{}");
	}

}
