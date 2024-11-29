package com.sbland.exrate.domain;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExRate(String result, Map<String, BigDecimal> rates) {

}
