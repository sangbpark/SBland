package com.sbland.ebay;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EbayDataService {
    private final RestTemplate restTemplate;
    private final EbayAuthService ebayAuthService;

    public List<Map<String, Object>> getItemsBySeller(String sellerId) {
        String accessToken = ebayAuthService.getAccessToken();
        String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=warhammer&filter=seller:" + sellerId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("itemSummaries")) {
            return (List<Map<String, Object>>) body.get("itemSummaries");
        }
        return Collections.emptyList();
    }
}