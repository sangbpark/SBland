package com.sbland.user;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sbland.common.reponse.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class UserRestControllerTest {
	@Autowired
	UserRestController userRestController;
	
	@Transactional
	@Test
	void 회원가입테스트() {
		LocalDate date = LocalDate.now();
		Response<String> result = userRestController.userSignUp("aaaa", "1234567", "ㅎㅇ", "gd@dfas.com", date, "TEST");
		log.info("[테스트] result:{}", result);
	}

}
