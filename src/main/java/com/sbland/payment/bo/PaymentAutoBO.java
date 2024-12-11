package com.sbland.payment.bo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.sbland.payment.dto.PortoneToken;

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
						headers.setBearerAuth(accessToken);}
					)
					.retrieve()
			        .bodyToMono(Map.class);
	};
	
	public Mono<Map> getPaymentCancel(String impUid, String merchantUid, String reason, int amount, String accessToken) {
    	return webClient.post()
					.uri(apiUrl + "/payments/cancel")
					.headers(headers -> {
						headers.setBearerAuth(accessToken);}
					)
					.bodyValue(Map.of(
							"imp_uid",impUid
							,"merchant_uid",merchantUid
							,"reason",reason
							,"amount",amount))
					.retrieve()
			        .bodyToMono(Map.class);
	}; 
	
	@CachePut(value = "portoneTokens", key = "'portoneToken'")
	public PortoneToken updatePortoneToken() {
	    Map<String, Object> responseData = getAccessToken().block();
	    Map<String, Object> tokenMap = (Map<String, Object>) responseData.get("response");

	    return PortoneToken
	            .builder()
	            .accessToken((String) tokenMap.get("access_token"))
	            .expiredAt(LocalDateTime.ofInstant(
	                    Instant.ofEpochSecond((Integer) tokenMap.get("expired_at")),
	                    ZoneId.of("Asia/Seoul")
	            ))
	            .now(LocalDateTime.ofInstant(
	                    Instant.ofEpochSecond((Integer) tokenMap.get("now")),
	                    ZoneId.of("Asia/Seoul")
	            ))
	            .build();
	}
	
	@Cacheable(value = "portoneTokens", key = "'portoneToken'")
	public PortoneToken getPortoneToken() {
		Map<String, Object> responseData = getAccessToken().block();
		Map<String, Object> tokenMap =(Map<String, Object>) responseData.get("response");
	    return PortoneToken
	    		.builder()
	    		.accessToken((String)tokenMap.get("access_token"))
	    		.expiredAt(LocalDateTime.ofInstant(
	    			        Instant.ofEpochSecond((Integer) tokenMap.get("expired_at")),
	    			        ZoneId.of("Asia/Seoul"))
	    		)
	    		.now(LocalDateTime.ofInstant(
	    			        Instant.ofEpochSecond((Integer) tokenMap.get("now")),
	    			        ZoneId.of("Asia/Seoul"))
	    		)
	    		.build();
	}
}
