package com.sbland.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder(toBuilder=true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EbayProductImageDTO {
	private Long productId;
	private String productName;
	private String url;
	private Boolean isThumbnail;
	private int position;
}
