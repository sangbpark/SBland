package com.sbland.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
    	  ObjectMapper objectMapper = JsonMapper.builder()
    	            .addModule(new JavaTimeModule())
    	            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    	            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    	            .build();
        return objectMapper;
    }
}
