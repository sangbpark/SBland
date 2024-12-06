package com.sbland.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.payment.domain.Payment;

@Mapper
public interface PaymentMapper {
	
	public Payment selectPaymentByUserId(Long userId);
	
	public Payment selectPaymentByMerchantUid(String merchantUid);
	
	public Payment selectPaymentByImpUid(String impUid);
	
	public int insertPayment(Payment payment);
}
