package com.sbland.exrate.bo;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sbland.exrate.domain.ExRate;
import com.sbland.exrate.provider.ExRateIf;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExRateBO {
	private final ExRateIf exRateIf;
	
	public int calculateExRate(BigDecimal price, String currency) {
		ExRate exRate = exRateIf.extract(currency).block();
		BigDecimal rate = exRate.rates().get("KRW");        
        BigDecimal calculatedPrice = price.multiply(rate); 
        int scale = 100;
        int roundedPrice = Math.round(calculatedPrice.intValue() / (float) scale) * scale;
        return roundedPrice;
	}
}
