package com.sbland.ebay.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EbayAuthBO {
    private final WebClient webClient;
    private final EbayAuth ebayAuth;

    @Value("${ebay.client-id}")
    private String clientId;

    @Value("${ebay.client-secret}")
    private String clientSecret;
    
    @Value("${ebay.token-url}")
    private String tokenUrl;
    
    @Value("${ebay.grant_type}")
    private String grantType;
    
    @Value("${ebay.scope}")
    private String scope;

    public Mono<String> getAccessToken() {
    	return ebayAuth.getAccessToken(clientId, clientSecret, webClient, tokenUrl, grantType, scope);
    }
}
