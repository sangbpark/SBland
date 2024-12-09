package com.sbland.payment.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.payment.PaymentRestController;
import com.sbland.payment.dto.PortoneToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@EnableCaching
class PaymentServiceBOTest {
	@Autowired
	PaymentServiceBO paymentServiceBO;
	@Autowired
	PaymentRestController paymentRestController;

	@Test
	void 캐시테스트() {
		paymentServiceBO.getPortoneToken();															
		PortoneToken portoneToken = paymentServiceBO.getPortoneToken();
		portoneToken = paymentServiceBO.validateAndGetPortoneToken(portoneToken);
		PortoneToken newPortoneToke = paymentServiceBO.validateAndGetPortoneToken(portoneToken);
		log.info("[테스트] portoneToken:{}, new:{}", portoneToken, newPortoneToke);
	}
	
}
