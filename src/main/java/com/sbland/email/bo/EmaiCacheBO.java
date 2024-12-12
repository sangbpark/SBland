package com.sbland.email.bo;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.sbland.aop.cache.FindCache;
import com.sbland.common.keys.KeysGenerator;
import com.sbland.email.dto.EmailVerifyDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmaiCacheBO {
	private final EmailBO emailBO;
       
    @CachePut(value = "EmailVerifyDTO", key = "#root.args[0]")
    public EmailVerifyDTO createVerifyEmail(String email) {
    	String key = new KeysGenerator().getSalt(12);
        String body = "안녕하세요! 아래 보안번호를 입력하여 이메일 인증을 완료하세요:\n" + key;
        emailBO.sendEmail(email,"SBLAND 이메일 인증", body);
    	return EmailVerifyDTO
    			.builder()
    			.salt(key)
    			.build();
    }
      
    
    @FindCache(value = "EmailVerifyDTO", key = "#root.args[0]")
    public EmailVerifyDTO getVerifyEmail(String email) {
    	return new EmailVerifyDTO();
    }
    
    @CacheEvict(value = "EmailVerifyDTO", key = "#root.args[0]")
    public void deleteVeifyEmail(String email) {    	
    }
}
