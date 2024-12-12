package com.sbland.common.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.objectmapper.provider.CamelObjectMapper;
import com.sbland.common.objectmapper.provider.SnakeObjectMapper;

public class ObjectMapperFactory {
	
	public ObjectMapper getCamelObjectMapper() {
		return new CamelObjectMapper().setConfigObjectMapper();
	}
	
	public ObjectMapper getSnakeObjectMapper() {
		return new SnakeObjectMapper().setConfigObjectMapper();
	}
}
