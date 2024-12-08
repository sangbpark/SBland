package com.sbland.payment.bo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	@Value("${portone.imp-key}")
	private String impKey;
	public Mono<Map> getAccessToken() {
	      return webClient.post()
	              .uri(apiUrl + "/users/getToken")
	              .headers(headers -> {headers.setContentType(MediaType.APPLICATION_JSON);})
	              .bodyValue(Map.of(
	            		  "imp_key",apiKey,
	            		  "imp_secret",apiSecret)
	               )
	              .retrieve()
	              .bodyToMono(Map.class);
	};
	
	public Mono<Map> getAccessTokenByRefreshToken(String refreshToken) {
		return webClient.post()
	              .uri(apiUrl + "/token/refresh")
	              .headers(headers -> {headers.setContentType(MediaType.APPLICATION_JSON);})
	              .bodyValue(Map.of("refreshToken",refreshToken))
	              .retrieve()
	              .bodyToMono(Map.class);
	}
	
	public String getImpKey() {
		return this.impKey;
	};
	
	public Mono<Map> getVerify(String impUid, String accessToken) {
    	return webClient.get()
					.uri(apiUrl + "/payments/" + impUid)
					.headers(headers -> {
						headers.setContentType(MediaType.APPLICATION_JSON);
						headers.setBearerAuth(accessToken);}
					)
					.retrieve()
			        .bodyToMono(Map.class);
	};
}
