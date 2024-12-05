package com.sbland.oderdetail.domain;

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
public class OrderDetail {
	private Long id;
	private Long orderId;
	private Long productId;
	private int productCount;
	private int price;
	private int totalPrice;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
