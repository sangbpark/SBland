package com.sbland;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@SpringBootTest
class SbLandApplicationTests {

	@Test
	void contextLoads() {
		// unit test
		int a = 5;
		int b = 10;
		assertEquals(15, a + b );
		log.info("첫번째 메소드");
	}
	
	@Test
	void 테스트() {
		log.info("두번째 메소드");
		// given - 준비
		
		// when - 실행
		
		// then - 검증
	}
}
