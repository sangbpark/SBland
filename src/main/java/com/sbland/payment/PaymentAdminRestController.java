package com.sbland.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.Response;
import com.sbland.payment.bo.PaymentServiceBO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin/payment")
@RequiredArgsConstructor
@RestController
public class PaymentAdminRestController {
	private final PaymentServiceBO paymentServiceBO;

	@PostMapping("/cancel")
	public Response<Boolean> cancelPayment(
			@RequestParam("orderId") Long orderId) {
		return paymentServiceBO.updatePaymentCancel(orderId, "구매취소", 0);
	}
}
