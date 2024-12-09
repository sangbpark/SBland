package com.sbland.payment.dto;

import java.util.List;

import com.sbland.oderdetail.dto.OrderDetailPaymentDTO;

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
public class PaymentRequestDTO {
    private String impUid;
    private String merchantUid;
    private int deliveryfee;
    private String shippingAddress;
    private int amount;
    private List<OrderDetailPaymentDTO> orderDetailMapList;
}