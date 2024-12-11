package com.sbland.common.keys.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sbland.common.keys.provider.MerchantUidProvider;
import com.sbland.common.keys.provider.SaltProvider;

@Component
public class KeysGeneratorFactory {
	
	@Bean
	public MerchantUidProvider getMerchantUidProvider() {
		return new MerchantUidProvider();
	}

	@Bean
	public SaltProvider getSaltProvider() {
		return new SaltProvider();
	}
}
