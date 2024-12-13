package com.sbland.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbland.common.reponse.Response;
import com.sbland.payment.bo.PaymentServiceBO;
import com.sbland.payment.dto.PaymentRequestDTO;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/payment")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PaymentRestController {
	private final PaymentServiceBO paymentServiceBO;
	
	@PostMapping("/verify")
	public Response<Boolean> paymentVerify(
			@RequestBody PaymentRequestDTO paymentRequestDTO) {
		return paymentServiceBO.addPaymentflow(
				paymentRequestDTO.getImpUid()
				, paymentRequestDTO.getUserId()
				, paymentRequestDTO.getDeliveryfee()
				, paymentRequestDTO.getShippingAddress()
				, paymentRequestDTO.getOrderDetailMapList()
				);
	}
}
