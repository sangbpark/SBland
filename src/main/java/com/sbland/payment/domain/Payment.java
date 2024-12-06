package com.sbland.payment.domain;

import java.time.LocalDateTime;

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
public class Payment {
	private Long id;
	private String impUid;
	private String merchantUid;
	private Long orderId;
	private Long userId;
	private int amount;
	private String paymentMethod;
	private String status;
	private LocalDateTime paidAt;
	private String cancelReason;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
