package com.sbland.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbland.oderdetail.dto.OrderDetailDTO;

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
public class OrderDTO {
	private Long id;
	private Long userId;
	private int amount;
	private int deliveryfee;
	private String status;
	private String shippingAddress;
	private List<OrderDetailDTO> orderDetailDTOList;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;
}
