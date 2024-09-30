package com.beesocial.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user-management-service", r -> r
						.path("/user-management-service/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://user-management-service"))
				.route("content-management-service", r -> r
						.path("/content-management-service/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://content-management-service"))
				.route("opportunity-management-service", r -> r
						.path("/opportunity-management-service/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://opportunity-management-service"))
				.route("event-management-service", r -> r
						.path("/event-management-service/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://event-management-service"))
				.build();
	}
}
