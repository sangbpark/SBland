package com.sbland.payment.bo;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.payment.domain.Payment;
import com.sbland.payment.dto.PortoneToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class PaymentAutoBOTest {
	@Autowired
	PaymentAutoBO paymentAutoBO;
	@Autowired
	PaymentBO paymentBO;
	@Autowired
	PaymentServiceBO paymentServiceBO;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void 포트원v2토큰테스트() {
		Map<String,String> result = paymentAutoBO.getAccessToken().block();	
		log.info("[dfasdf] result:{}",result.get("accessToken"));
	}
	
	@Test 
	void 포트원검증테스트() {
		PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
		Map<String, Object> response = (Map<String, Object>) paymentAutoBO.getVerify("imp_339482206636", portoneToken.getAccessToken()).block().get("response");
		Payment payment = objectMapper.convertValue(response, Payment.class);
		log.info("payment:{}",payment);
	}
	
	@Test
	void 포트원() {
		PortoneToken portoneToken = paymentAutoBO.getPortoneToken();
		log.info("[dfasdf] result:{}",portoneToken);
	}

}
