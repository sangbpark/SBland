package com.sbland.payment.bo;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sbland.payment.dto.PortoneToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceBO {
	private final PaymentBO paymentBO;
	private final PaymentAutoBO paymentAutoBO;
	
	@Cacheable(value = "portoneTokens", key = "'portoneToken'")
	public PortoneToken getPortoneToken() {
		Map<String, String> tokenMap = paymentAutoBO.getAccessToken().block(); 
		
	    return PortoneToken.builder()
	            .accessToken(tokenMap.get("accessToken"))
	            .accessDeadline(LocalDate.now().plusDays(1L))
	            .refreshToken(tokenMap.get("refreshToken"))
	            .refreshDeadline(LocalDate.now().plusDays(7L))
	            .build();
	}

}
