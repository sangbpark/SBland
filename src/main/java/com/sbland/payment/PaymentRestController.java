package com.sbland.payment;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.common.reponse.HttpStatusCode;
import com.sbland.common.reponse.Response;
import com.sbland.payment.bo.PaymentServiceBO;
import com.sbland.payment.domain.Payment;
import com.sbland.user.dto.UserSessionDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PaymentRestController {
	private final PaymentServiceBO paymentServiceBO;
	private final ObjectMapper objectMapper;
	
	@PostMapping("/verify")
	public Response<Boolean> paymentVerify(
			@RequestParam("impUid") String impUid, 
			@RequestParam("merchantUid") String merchantUid, 
			@RequestParam("amount") int amount, 
			@RequestParam("deliveryfee") int deliveryfee,  
			@RequestParam("shippinAddress") String shippinAddress,  
			@RequestParam("orderDetailMapList") String orderDetailMapListJson,
			HttpSession session) {
		UserSessionDTO userSession = (UserSessionDTO)session.getAttribute("userSession");

		Response<Boolean> Response = paymentServiceBO.verifyPayment(impUid, userSession.getId(), deliveryfee, shippinAddress, orderDetailMapListJson);
		
		return null;
	}
}
