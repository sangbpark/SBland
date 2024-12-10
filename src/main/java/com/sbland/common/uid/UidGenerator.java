package com.sbland.common.uid;

import org.springframework.stereotype.Component;

import com.sbland.common.uid.component.UidGeneratorFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UidGenerator {
	private final UidGeneratorFactory uidGeneratorFactroy;
	
	public String getMerchantUid() {
		return uidGeneratorFactroy.getMerchantUidProvider().createUid("order");
	}
	
	public String getEmailVerifyUid() {
		return uidGeneratorFactroy.getMerchantUidProvider().createUid("email");
	}
}
