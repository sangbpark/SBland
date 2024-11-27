package com.sbland.ebay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EbayAuthService {
    private final RestTemplate restTemplate;
    private final EbayAuth ebayAuth;

    @Value("${ebay.client-id}")
    private String clientId;

    @Value("${ebay.client-secret}")
    private String clientSecret;

    public String getAccessToken() {
    	return ebayAuth.getAccessToken(clientId, clientSecret, restTemplate);
    }
}
