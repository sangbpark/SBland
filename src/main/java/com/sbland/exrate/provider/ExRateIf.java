package com.sbland.exrate.provider;

import com.sbland.exrate.domain.ExRate;

import reactor.core.publisher.Mono;

public interface ExRateIf {

	public Mono<ExRate> extract(String response);
}
