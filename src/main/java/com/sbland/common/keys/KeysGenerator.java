package com.sbland.common.keys;

import org.springframework.stereotype.Component;

import com.sbland.common.keys.component.KeysGeneratorFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class KeysGenerator {
	private final KeysGeneratorFactory uidGeneratorFactroy;
	
	public String getMerchantUid() {
		return uidGeneratorFactroy.getMerchantUidProvider().createUid("order");
	}
	
	public String getEmailVerifyUid() {
		return uidGeneratorFactroy.getMerchantUidProvider().createUid("email");
	}
	
	public String getSalt(int saltLength) {
		return uidGeneratorFactroy.getSaltProvider().generateSalt(saltLength);
	}
}
