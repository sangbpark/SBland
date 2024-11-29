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
public class ProductImage {
	private Long id;
	private Long productId;
	private String url;
	private Boolean isThumbnail;
	private int position;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
