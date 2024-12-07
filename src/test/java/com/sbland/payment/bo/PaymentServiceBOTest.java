package com.sbland.payment.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

import com.sbland.payment.dto.PortoneToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@EnableCaching
class PaymentServiceBOTest {
	@Autowired
	PaymentServiceBO paymentServiceBO;

	@Test
	void 캐시테스트() {
		paymentServiceBO.getPortoneToken();															
		paymentServiceBO.getPortoneToken();															
	}

}
