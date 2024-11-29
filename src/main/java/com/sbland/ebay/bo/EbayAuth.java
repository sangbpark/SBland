package com.sbland.ebay.bo;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public interface EbayAuth {
    public Mono<String> getAccessToken(String clientId, String clientSecret, WebClient webClient, String tokenUrl, String grantType, String scope);
}
