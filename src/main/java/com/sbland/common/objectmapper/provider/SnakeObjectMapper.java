package com.sbland.common.objectmapper.provider;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbland.common.objectmapper.serializer.StringTimestampToLocalDateTimeDeserializer;

public class SnakeObjectMapper implements ConfigObjectMapperImp {

	@Override
	public ObjectMapper setConfigObjectMapper() {
  	  return JsonMapper.builder()
	            .addModule(new JavaTimeModule()
	            		.addDeserializer(LocalDateTime.class, new StringTimestampToLocalDateTimeDeserializer())
	            )
	            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
	            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
	            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
	            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
	            .build();
	}
	
}
