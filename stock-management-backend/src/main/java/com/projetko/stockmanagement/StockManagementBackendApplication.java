package com.projetko.stockmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StockManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockManagementBackendApplication.class, args);
	}

}
