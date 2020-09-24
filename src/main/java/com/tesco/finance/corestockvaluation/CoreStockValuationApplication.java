package com.tesco.finance.corestockvaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class CoreStockValuationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreStockValuationApplication.class, args);
	}

	
}
	 