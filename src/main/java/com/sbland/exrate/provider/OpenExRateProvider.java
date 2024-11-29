package com.sbland.exrate.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.sbland.exrate.domain.ExRate;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Primary
@RequiredArgsConstructor
@Component("openExRate")
public class OpenExRateProvider implements ExRateIf {
	private final WebClient webClient;
	
	@Value("${exrate.url}")
	private String url;
	
	@Override
	public Mono<ExRate> extract(String response) {
		String finalUrl = url + response;
		return webClient
				.get()
				.uri(finalUrl)
				.retrieve()
				.bodyToMono(ExRate.class);
	}

}
