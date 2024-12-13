package com.sbland.payment.domain;

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
public class Payment {
	private Long id;
	private String impUid;
	private String merchantUid;
	private Long orderId;
	private Long userId;
	private int amount;
	private String payMethod;
	private String status;
	private LocalDateTime paidAt;
	private String receiptUrl;
	private String cardName;
	private int cancelAmount;
	private String cancelReason;
	private LocalDateTime cancelledAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
