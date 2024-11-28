package com.sbland.ebay;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EbayDataService {
    private final WebClient webClient;
    private final EbayAuthService ebayAuthService;

    public Mono<List<Map<String, Object>>> getItems(String keyword, int offset) {
        return ebayAuthService.getAccessToken()
                .flatMap(accessToken -> 
                    webClient.get()
                            .uri("https://api.ebay.com/buy/browse/v1/item_summary/search?q=" + keyword + "&limit=100&offset=" + offset)
                            .headers(headers -> headers.setBearerAuth(accessToken))
                            .retrieve()
                            .bodyToMono(Map.class)
                            .map(response -> {
                                if (response != null && response.containsKey("itemSummaries")) {
                                    return (List<Map<String, Object>>) response.get("itemSummaries");
                                }
                                return Collections.emptyList();
                            })
                );
    }
}