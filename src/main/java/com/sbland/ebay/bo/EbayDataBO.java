package com.sbland.ebay.bo;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.product.domain.EbayProduct;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EbayDataBO {
    private final WebClient webClient;
    private final EbayAuthBO ebayAuthService;
    private final ObjectMapper objectMapper;

    public Mono<List<EbayProduct>> getItems(String keyword, int offset) {
        return ebayAuthService.getAccessToken()
                .flatMap(accessToken -> 
                    webClient.get()
                            .uri("https://api.ebay.com/buy/browse/v1/item_summary/search?q=" + keyword + "&limit=100&offset=" + offset)
                            .headers(headers -> headers.setBearerAuth(accessToken))
                            .retrieve()
                            .bodyToMono(Map.class)
                            .map(response -> {
                                if (response != null && response.containsKey("itemSummaries")) {
                                	  List<Map<String, Object>> productList = (List<Map<String, Object>>) response.get("itemSummaries");
                                      return productList.stream()
                                    		  .filter(product -> {
                                    			  					String userName = (String) ((Map<String, Object>) product.get("seller")).get("username");
                                    			  					return "flipsidegaming".equals(userName);
                                    			  				})
                                              .map(product -> objectMapper.convertValue(product, EbayProduct.class))
                                              .toList();
                                }
                                return Collections.emptyList();
                            })
                );
    }
}