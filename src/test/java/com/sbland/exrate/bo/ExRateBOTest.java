package com.sbland.exrate.bo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExRateBOTest {
	@Autowired
	ExRateBO exRateBO;

	@Test
	void 환율테스트() {
		int price = exRateBO.calculateExRate(BigDecimal.valueOf(10), "usd");
		assertEquals(price, 14000);
	}

}
