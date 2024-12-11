package com.sbland.oderdetail.dto;

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
public class OrderDetailPaymentDTO {
	private Long productId;
	private int productCount;
	private int productPrice;
	private int totalPrice;
}
