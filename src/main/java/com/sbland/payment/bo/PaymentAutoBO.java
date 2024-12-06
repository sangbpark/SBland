package com.sbland.payment.bo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PaymentAutoBO {
	private final WebClient webClient;
	
	@Value("${portone.api-url}")
    private String apiUrl;
	
	@Value("${portone.rest-api-key}")
    private String apiKey; 
	
	@Value("${portone.rest-api-secret}")
    private String apiSecret;
	
	public Mono<String> getAccessToken() {
	        
	      return webClient.post()
	              .uri(apiUrl + "/login/api-secret")
	              .headers(headers -> {headers.setContentType(MediaType.APPLICATION_JSON);})
	              .bodyValue(Map.of("apiSecret",apiSecret))
	              .retrieve()
	              .bodyToMono(Map.class)
	              .map(response -> response.get("accessToken").toString());
	};
}
