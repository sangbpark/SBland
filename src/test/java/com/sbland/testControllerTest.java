package com.sbland;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class testControllerTest {
	@Autowired
	testController testController;

	@Test
	void 오브젝트맵퍼테스트() {
		testController.test7();	
	}

}
