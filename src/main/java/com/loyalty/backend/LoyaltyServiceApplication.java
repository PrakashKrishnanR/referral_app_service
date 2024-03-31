package com.loyalty.backend;

import com.loyalty.backend.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class LoyaltyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoyaltyServiceApplication.class, args);
	}
}
