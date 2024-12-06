package com.sbland.payment.bo;

import org.springframework.stereotype.Service;

import com.sbland.common.reponse.Response;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentServiceBO {
	private final PaymentBO paymentBO;

}
