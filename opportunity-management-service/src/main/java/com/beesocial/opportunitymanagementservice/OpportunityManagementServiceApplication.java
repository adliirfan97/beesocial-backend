package com.beesocial.opportunitymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OpportunityManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpportunityManagementServiceApplication.class, args);
	}

}
