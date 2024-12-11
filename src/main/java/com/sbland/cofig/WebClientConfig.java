package com.sbland.cofig;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbland.objectmapper.StringTimestampToLocalDateTimeDeserializer;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
    
    @Bean
    public ObjectMapper objectMapper() {
    	  ObjectMapper objectMapper = JsonMapper.builder()
    	            .addModule(new JavaTimeModule()
    	            		.addDeserializer(LocalDateTime.class, new StringTimestampToLocalDateTimeDeserializer())
    	            )
    	            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    	            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    	            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    	            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
    	            .build();
    
        return objectMapper;
    }
}
