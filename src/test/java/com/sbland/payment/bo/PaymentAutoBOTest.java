package com.sbland.payment.bo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class PaymentAutoBOTest {
	@Autowired
	PaymentAutoBO paymentAutoBO;

	@Test
	void 포트원v2토큰테스트() {
		Map<String,String> result = paymentAutoBO.getAccessToken().block();	
		log.info("[dfasdf] result:{}",result.get("accessToken"));
	}

}
