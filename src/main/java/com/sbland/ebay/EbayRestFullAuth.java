package com.sbland.ebay;

import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Primary
@Component("restFullAuth")
public class EbayRestFullAuth implements EbayAuth {

	@Override
	public Mono<String> getAccessToken(String clientId ,String clientSecret, WebClient webClient) {
	      String url = "https://api.ebay.com/identity/v1/oauth2/token";
	      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	      body.add("grant_type", "client_credentials");
	      body.add("scope", "https://api.ebay.com/oauth/api_scope");
	      
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
