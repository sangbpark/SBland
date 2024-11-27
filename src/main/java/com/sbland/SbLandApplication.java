package com.sbland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SbLandApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbLandApplication.class, args);
	}

}
