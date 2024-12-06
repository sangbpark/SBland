package com.sbland.common.uid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UidGeneratorTest {
	@Autowired
	UidGenerator uidGenerator;

	@Test
	void uid생성태스트() {
		String result = uidGenerator.getMerchantUid();
		log.info("[테스트] result:{}",result);
	}

}
