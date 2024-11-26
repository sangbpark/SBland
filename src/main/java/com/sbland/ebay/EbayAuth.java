package com.sbland.ebay;

import org.springframework.web.client.RestTemplate;

public interface EbayAuth {
    public String getAccessToken(String clientId, String clientSecret, RestTemplate restTemplate);
}
