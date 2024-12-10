package com.sbland.email.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {
	@Autowired
	EmailServiceBO emailServiceBO;
	@Test
	void 이메일발송테스트() {
//		emailServiceBO.sendVerifyEmail("qkrtkdqo94@naver.com", "SBLAND 첫 테스트", "성공");
	}
	
	@Test
	void 이메일인증테스트() {
		emailServiceBO.sendVerifyEmail("qkrtkdqo94@naver.com");
	}

}
