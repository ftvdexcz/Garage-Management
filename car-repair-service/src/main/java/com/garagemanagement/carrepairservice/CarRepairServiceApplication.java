package com.garagemanagement.carrepairservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CarRepairServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRepairServiceApplication.class, args);
	}
}
