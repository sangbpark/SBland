package com.sbland.common.uid.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sbland.common.uid.provider.MerchantUidProvider;

@Component
public class UidGeneratorFactory {
	
	@Bean
	public MerchantUidProvider getMerchantUidProvider() {
		return new MerchantUidProvider();
	}

}
