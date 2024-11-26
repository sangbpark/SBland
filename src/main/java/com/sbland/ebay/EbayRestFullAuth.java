package com.sbland.ebay;

import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Primary
@Component("restFullAuth")
public class EbayRestFullAuth implements EbayAuth {

	@Override
	public String getAccessToken(String clientId ,String clientSecret, RestTemplate restTemplate) {
	      String url = "https://api.ebay.com/identity/v1/oauth2/token";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setBasicAuth(clientId, clientSecret);
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	        body.add("grant_type", "client_credentials");
	        body.add("scope", "https://api.ebay.com/oauth/api_scope");

	        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

	        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
	        return response.getBody().get("access_token").toString();
	}

}
