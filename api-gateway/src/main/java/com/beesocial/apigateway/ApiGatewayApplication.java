package com.beesocial.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRoutes(RouteLocatorBuilder builder, EurekaDiscoveryClient discoveryClient) {
		return builder.routes()
				.route("user-management-service", r -> r
						.path("/user-management-service/**")
						.filters(f -> f.stripPrefix(1))
						.uri(getServiceUri(discoveryClient, "user-management-service")))
//				.route("user-management-service", r -> r
//						.path("/user-management-service/**")
//						.filters(f -> f.stripPrefix(1))
//						.uri(getServiceUri(discoveryClient, "user-management-service")))
				.build();
	}

	// Dynamic Service Discovery
	private String getServiceUri(DiscoveryClient discoveryClient, String serviceName) {
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
		if (instances != null && !instances.isEmpty()) {
			return instances.get(0).getUri().toString();
		}
		throw new RuntimeException("No instances available for service: " + serviceName);
	}

}
