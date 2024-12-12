package com.sbland.common.keys;

import com.sbland.common.keys.provider.SaltProvider;
import com.sbland.common.keys.provider.UidProvider;

public class KeysGenerator {
	

	public String getMerchantUid() {
		return new UidProvider().createUid("order");
	}
	
	public String getEmailVerifyUid() {
		return new UidProvider().createUid("email");
	}

	public String getSalt(int saltLength) {
		return new SaltProvider().generateSalt(saltLength);
	}
}
