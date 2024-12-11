package com.sbland.payment.bo;

import org.springframework.stereotype.Service;

import com.sbland.payment.domain.Payment;
import com.sbland.payment.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaymentBO {
	private final PaymentMapper paymentMapper;

	
	public Payment getPaymentByUserId(Long userId) {
		return paymentMapper.selectPaymentByUserId(userId);
	};
	
	public Payment getPaymentByMerchantUid(String merchantUid) {
		return paymentMapper.selectPaymentByMerchantUid(merchantUid);
	};
	
	public Payment getPaymentByImpUid(String impUid) {
		return paymentMapper.selectPaymentByImpUid(impUid);
	};
	
	public int addPayment(Payment payment) {
		return paymentMapper.insertPayment(payment);
	};
	
	public int updatePayment(Payment payment) {
		return paymentMapper.updatePayment(payment);
	}
}
