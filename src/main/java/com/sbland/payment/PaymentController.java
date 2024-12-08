package com.sbland.payment;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbland.payment.bo.PaymentServiceBO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/payment")
@Controller
public class PaymentController {
	private final PaymentServiceBO paymentServiceBO;
	private final ObjectMapper objectMapper;
	
//	@GetMapping("/payment-view")
//	public String paymentView(
//			@RequestParam("amount") int amount, 
//			@RequestParam("deliveryfee") int deliveryfee,  
//			@RequestParam("orderDetailMapList") String orderDetailMapListJson,
//			Model model) {		 		 
//		try {
//			List<Map<String, Object>> orderDetailMapList = objectMapper.readValue(orderDetailMapListJson, new TypeReference<>() {});
//			
//			model.addAttribute("amount", amount);
//			model.addAttribute("deliveryfee", deliveryfee);
//			model.addAttribute("orderDetailMapList", orderDetailMapList);
//			model.addAttribute("merchantUid", paymentServiceBO.getMerchantUid());
//			model.addAttribute("impKey", paymentServiceBO.getImpKey());
//	        return "payment/payment";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error";
//		} 
//	     
//	}
	
}
