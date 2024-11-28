package com.sbland.ebay;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component("tradingAuth")
public class EbayTradingAuth implements EbayAuth{

	@Override
	public Mono<String> getAccessToken(String clientId, String clientSecret, WebClient webClient) {
		   String url = "https://api.ebay.com/identity/v1/oauth2/token";

		    return webClient.post()
		              .uri(url)
		              .headers(headers -> {
		                  headers.setBasicAuth(clientId, clientSecret); 
		                  headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		              })
		              .bodyValue( 
		                      Map.of(
		                              "grant_type", "client_credentials",
//		                              "code", authorizationCode,
		                              "scope", "https://api.ebay.com/oauth/api_scope"
		                      )
		              )
		              .retrieve()
		              .bodyToMono(Map.class)
		              .map(response -> response.get("access_token").toString());
	}

}
