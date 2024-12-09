package com.sbland.payment.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@JsonProperty("imp_uid")
	private String impUid;
	@JsonProperty("merchant_uid")
	private String merchantUid;
	private Long orderId;
	private Long userId;
	private int amount;
	@JsonProperty("pay_method")
	private String payMethod;
	private String status;
	@JsonProperty("card_name")
	private String cardName;
	@JsonProperty("receipt_url")
	private String receiptUrl;
	@JsonProperty("paid_at")
	private String paidAt;
	@JsonProperty("cancel_reason")
	private String cancelReason;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
