package com.sbland.shoppingcart.domain;

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
public class Shoppingcart {
	private Long userId;
	private Long productId;
	private int quantity;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
