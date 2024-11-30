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
public class ProductThumbnailCardDTO {
	private Long productId;
	private String ThumbnailImage;
	private String productName;
	private int productPrice; 
}
