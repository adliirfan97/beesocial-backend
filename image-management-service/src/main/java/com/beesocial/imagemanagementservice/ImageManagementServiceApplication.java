package com.beesocial.imagemanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ImageManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageManagementServiceApplication.class, args);
	}

}
