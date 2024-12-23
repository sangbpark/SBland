package com.sbland.product.dto;

import java.util.List;

import com.sbland.product.domain.ProductImage;

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
public class ProductDetailCardDTO {
	private Long id;
	private List<ProductImage> url;
	private String name;
	private int price; 
	private int quantity;
}
