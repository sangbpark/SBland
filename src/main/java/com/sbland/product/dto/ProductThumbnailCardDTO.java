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
	private Long id;
	private String thumbnailImage;
	private String name;
	private int price; 
	private int quantity;
}
