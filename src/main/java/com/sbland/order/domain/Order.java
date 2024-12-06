package com.sbland.order.domain;

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
public class Order {
	private Long id;
	private String merchantUid;
	private Long userId;
	private int amount;
	private int deliveryfee;
	private String status;
	private String shippingAddress;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
