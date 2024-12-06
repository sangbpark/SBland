package com.sbland.payment.bo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
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
	
	public Mono<Map<String, String>> getAccessToken() {
	      return webClient.post()
	              .uri(apiUrl + "/login/api-secret")
	              .headers(headers -> {headers.setContentType(MediaType.APPLICATION_JSON);})
	              .bodyValue(Map.of("apiSecret",apiSecret))
	              .retrieve()
	              .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {});
	};
}
