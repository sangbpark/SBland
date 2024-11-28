package com.sbland.ebay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EbayAuthService {
    private final WebClient webClient;
    private final EbayAuth ebayAuth;

    @Value("${ebay.client-id}")
    private String clientId;

    @Value("${ebay.client-secret}")
    private String clientSecret;

    public Mono<String> getAccessToken() {
    	return ebayAuth.getAccessToken(clientId, clientSecret, webClient);
    }
}
