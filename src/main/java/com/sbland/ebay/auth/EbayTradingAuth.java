package com.sbland.ebay.auth;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.sbland.ebay.bo.EbayAuth;

import reactor.core.publisher.Mono;

@Component("tradingAuth")
public class EbayTradingAuth implements EbayAuth{

	@Override
	public Mono<String> getAccessToken(String clientId, String clientSecret, WebClient webClient, String tokenUrl, String grantType, String scope) {
		   String url = tokenUrl;
		   MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		      body.add("grant_type", grantType);
//		      body.add("code", authorizationCode);
		      body.add("scope", scope);
		    return webClient.post()
		              .uri(url)
		              .headers(headers -> {
		                  headers.setBasicAuth(clientId, clientSecret); 
		                  headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		              })
		              .bodyValue(body)
		              .retrieve()
		              .bodyToMono(Map.class)
		              .map(response -> response.get("access_token").toString());
	}

}
