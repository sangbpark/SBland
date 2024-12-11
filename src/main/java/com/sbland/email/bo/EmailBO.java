package com.sbland.email.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sbland.aop.cache.FindCache;
import com.sbland.common.keys.KeysGenerator;
import com.sbland.email.dto.EmailVerifyDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailBO {
	@Value("${spring.mail.username}")
	private String StoreEmail;
	
    private final JavaMailSender mailSender;
    private final KeysGenerator uidGenerator;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(StoreEmail);
        try {
            mailSender.send(message);
        } catch (MailException e) {
           log.info("[메일 발송] 메일 발송 실패 error:{}", e.getMessage());
        }
    }
       
    @CachePut(value = "EmailVerifyDTO", key = "#root.args[0]")
    public EmailVerifyDTO createVerifyEmail(String email) {
    	String key = uidGenerator.getSalt(12);
        String body = "안녕하세요! 아래 보안번호를 입력하여 이메일 인증을 완료하세요:\n" + key;
    	sendEmail(email,"SBLAND 이메일 인증", body);
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
