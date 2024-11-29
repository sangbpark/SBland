package com.sbland.product.domain;

import java.time.LocalDateTime;

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
public class Product {
	private Long id;
	private String name;
	private String description;
	private int price;
	private String status;
	private Integer category_code;
	private LocalDateTime updated_at;
	private LocalDateTime created_at;
}