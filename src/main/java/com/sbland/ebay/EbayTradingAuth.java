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
import org.springframework.web.client.RestTemplate;

@Component("tradingAuth")
public class EbayTradingAuth implements EbayAuth{

	@Override
	public String getAccessToken(String clientId, String clientSecret, RestTemplate restTemplate) {
		   String url = "https://api.ebay.com/identity/v1/oauth2/token";

		    HttpHeaders headers = new HttpHeaders();
		    headers.setBasicAuth(clientId, clientSecret);
		    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		    body.add("grant_type", "authorization_code");
//		    body.add("code", authorizationCode);
		    body.add("redirect_uri", "YourRedirectURI");

		    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

		    ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
		    return response.getBody().get("access_token").toString();
	}

}
