package com.sbland.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sbland.common.encrypt.EncryptInf;
import com.sbland.common.encrypt.EncryptUtils;
import com.sbland.common.encrypt.Jbcrypt;

@Configuration
public class EncryptConfig {
	
	@Bean
	public EncryptUtils encryptUtils(EncryptInf encryptInf) {
		return new EncryptUtils(jbcrypt());
	}
	
	@Bean
	public Jbcrypt jbcrypt() {
		return new Jbcrypt();
	}
}
