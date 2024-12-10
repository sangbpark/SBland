package com.sbland.payment.bo;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	@Test
	void 포트원v2토큰테스트() {
		Map<String,String> result = paymentAutoBO.getAccessToken().block();	
		log.info("[dfasdf] result:{}",result.get("accessToken"));
	}
	
	@Test 
	void 포트원검증테스트() {
		paymentAutoBO.getVerify("imp_281342585137", null);
	}
	
	@Test
	void 포트원() {
		PortoneToken portoneToken = paymentServiceBO.getPortoneToken();
		log.info("[dfasdf] result:{}",portoneToken);
	}

}
