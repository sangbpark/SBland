package com.sbland.payment.bo;

import java.time.LocalDate;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sbland.payment.dto.PortoneToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceBO {
	private final PaymentBO paymentBO;
	private final PaymentAutoBO paymentAutoBO;
	
	@Cacheable(value="portoneTokens")
	public PortoneToken getPortoneToken() {
			
		return paymentAutoBO.getAccessToken()
				.map(tokenMap -> PortoneToken
						.builder()
						.accessToken(tokenMap.get("accessToken"))
						.accessDeadline(LocalDate.now().plusDays(1L))
						.refreshToken(tokenMap.get("refreshToken"))
						.refreshDeadline(LocalDate.now().plusDays(7L))
						.build()
				).block();
	}

}
