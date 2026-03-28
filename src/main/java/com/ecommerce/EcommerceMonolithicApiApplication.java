package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // CleanupJob sınıfındaki Cronjob'ın çalışması için gerekli
public class EcommerceMonolithicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceMonolithicApiApplication.class, args);
	}
}