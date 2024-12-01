package com.sbland.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingcartCardDTO {
	private Long userId;
	private Long productId;
	private String ThumbnailImage;
	private String name;
	private int price;
	private int productCount;
}
