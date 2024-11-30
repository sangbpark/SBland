package com.sbland.product.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EbayProductDTO(String title
		, Map<String, Object> image
		, Map<String, Object> price
		, List<Map<String, String>> thumbnailImages) {

}
