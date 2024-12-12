package com.sbland.email.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailBO {
	@Value("${spring.mail.username}")
	private String StoreEmail;
	
	private final JavaMailSender mailSender;
	    
    @Async
    public void sendEmail(String to, String subject, String body) {
    	SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(StoreEmail);  
        mailSender.send(message);   
    }
}
