# Generating New Microservices

All essential dependencies are already included.

Generate the Spring project using [Spring Initializr](https://start.spring.io/):

- [User Management Service](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.beesocial&artifactId=user-management-service&name=user-management-service&description=Demo%20project%20for%20Spring%20Boot&packageName=com.beesocial.usermanagementservice&dependencies=devtools,lombok,web,cloud-eureka,actuator,cloud-feign)
- [Event Management Service](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.beesocial&artifactId=event-management-service&name=event-management-service&description=Demo%20project%20for%20Spring%20Boot&packageName=com.beesocial.eventmanagementservice&dependencies=devtools,lombok,web,cloud-eureka,actuator,cloud-feign)

# Eureka Server

[Netflix Eureka Guide](https://spring.io/guides/gs/service-registration-and-discovery)

Set up a Netflix Eureka Service Registry, `eureka-server` , and then two other services that will register with the Eureka Server. One of the services will call the other service using Web Client.

The `spring-cloud-starter-netflix-eureka-client` dependency must be included in all services that need to register with Eureka. This dependency enables inter-service communication using Feign Client.

Generate the Spring project with [Spring Initializr](https://start.spring.io/):

- [Eureka Server](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.example&artifactId=eureka-server&name=eureka-server&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.eurekaserver&dependencies=cloud-eureka-server)
- [User Management Service](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.beesocial&artifactId=user-management-service&name=user-management-service&description=Demo%20project%20for%20Spring%20Boot&packageName=com.beesocial.usermanagementservice&dependencies=devtools,lombok,web,cloud-eureka,actuator,cloud-feign)
- [Event Management Service](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.beesocial&artifactId=event-management-service&name=event-management-service&description=Demo%20project%20for%20Spring%20Boot&packageName=com.beesocial.eventmanagementservice&dependencies=devtools,lombok,web,cloud-eureka,actuator,cloud-feign)

## Start a Eureka Service Registry

To begin, you need to set up a Eureka Server. Add Spring Cloud's `@EnableEurekaServer` annotation to create a registry that other applications can use for communication.

```java
package com.example.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
```

By default, the registry also tries to register itself, so you need to disable that behavior. Additionally, it is a good convention to put this registry on a separate port when using it locally.

Add some properties to `application.yml` to handle these requirements, as the following listing shows:

```java
spring.application.name=eureka-server
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
logging.level.com.netflix.eureka=OFF
logging.level.com.netflix.discovery=OFF
```

You can now start the Eureka server by running the spring-boot application. You can check that the Eureka Server is up by viewing the [Eureka Dashboard](http://localhost:8761/).

## Register Services to Eureka Server

Now that you have started a service registry, you can stand up clients that interact with the registry.

Our applications, User Management Service and Event Management Service, automatically register with the Eureka server because we have `spring-cloud-starter-netflix-eureka-client` dependency.

To avoid port conflicts, set the `server.port` parameter in both User Management Service and Event Management Service:

User Management Service:

```java
spring.application.name=user-management-service
server.port=8081
```

Event Management Service:

```java
spring.application.name=event-management-service
server.port=8084
```

You can now see that the services are up in the [Eureka Dashboard](http://localhost:8761/).

# API Gateway

[Spring Cloud Gateway Guide](https://spring.io/projects/spring-cloud-gateway)

API Gateway manages routing, filtering, and proxying in microservice architectures. It helps address challenges such as API evolution, request routing, and legacy system integration. Additionally, API Gateway can reroute requests and modify headers for microservices.

Generate the Spring project with [Spring Initializr](https://start.spring.io/):

- [API Gateway](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.example&artifactId=api-gateway&name=api-gateway&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.apigateway&dependencies=actuator,cloud-eureka)

## Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

## Re-route Instructions

For the **API Gateway**, use **dynamic service discovery** (`lb://`) instead of hardcoded URIs or Feign clients. This approach harnesses the power of **Spring Cloud Gateway** and **Eureka** for efficient and resilient routing.

**Spring Cloud Gateway** enables **Ribbon-style service discovery** through a simple `lb://` prefix. This method seamlessly integrates with Eureka and manages load balancing automatically.

As you add more microservices to the system, you'll need to create corresponding routes in the API Gateway for each new service. These routes define how requests are directed to the appropriate microservice based on the path, filters, and target service URI (using `lb://`).

### Each Microservice Requires:

- **Unique route definitions** to map request paths (such as `/user-management-service/**`) to the appropriate service.
- **Path filters** (like `stripPrefix`) to modify the request before forwarding it.
- **Target URI** (`lb://service-name`) for service discovery and routing.

```java
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
                .route("event-management-service", r -> r
                        .path("/event-management-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://event-management-service"))
                .build();
    }
}

```

### Benefits of Using `lb://`

1. **Automatic Load Balancing**: The `lb://` prefix enables Spring Cloud Gateway to perform client-side load balancing automatically, using Eureka for service instance discovery.
2. **Cleaner Code**: There's no need to manually call `DiscoveryClient` to fetch service URIs. Spring Cloud Gateway and Eureka handle service discovery behind the scenes.
3. **Improved Resilience**: The `lb://` prefix is tailored for dynamic microservices environments, where services can scale up or down and instances may fluctuate.

# Feign Client

## **Ensure Eureka is Set Up**:

- All services that you want Feign to discover must be registered with a **Eureka Server**.
- The `spring-cloud-starter-netflix-eureka-client` dependency must be included in all services that need to register themselves with Eureka.

**Eureka Service Registration**: Your microservices (including the one you are calling via Feign) must be registered with **Eureka**. They do this by communicating with the Eureka Server and registering themselves under a specific service name (e.g., `firebase-storage-service`).

## **Enable Discovery in Your Feign Client Service:**

In the application where you're using Feign, ensure that **Eureka Discovery** is enabled by adding `@EnableFeignClients` to your Spring Boot main application class:

```java
package com.beesocial.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

}
```

## Create the Feign Client Interface

Define the Feign Client interface that will be used to call the `firebase-storage-service`. This interface will specify the API endpoints.

**Feign and Eureka Integration**: Feign integrates with **Eureka** via **Spring Cloud**. When you define a Feign Client with the `@FeignClient(name = "service-name")` annotation, Feign will use Eureka to discover the service by its name.

For Example:

```java
package com.beesocial.usermanagementservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "firebase-storage-service")
public interface FirebaseStorageClient {

    @GetMapping("/api/firebase/{collectionName}/{documentId}")
    Map<String, Object> getDocument(@PathVariable("collectionName") String collectionName, @PathVariable("documentId") String documentId);
}
```

In this case, Feign will look for the service named `firebase-storage-service` in the Eureka registry. It will automatically handle the service discovery, load balancing, and communication with instances of that service.

**Load Balancing**: Feign, in combination with **Ribbon** (which is part of Spring Cloud’s load balancing solution), automatically performs **client-side load balancing** across the instances of the service registered under the same name.

So, if there are multiple instances of `firebase-storage-service` running, Feign will distribute the load across them.

## Use Feign Client in the Controller

Inject the Feign Client into your controller and use it to make a call to the external service.

```java
package com.beesocial.usermanagementservice.controller;

import com.beesocial.usermanagementservice.feign.FirebaseStorageClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserManagementServiceController {

    private final FirebaseStorageClient firebaseStorageClient;

    public UserManagementServiceController(FirebaseStorageClient firebaseStorageClient) {
        this.firebaseStorageClient = firebaseStorageClient;
    }

    @GetMapping("/getDocument")
    public Map<String, Object> getDocument(@RequestParam String collectionName, @RequestParam String documentId) {
        return firebaseStorageClient.getDocument(collectionName, documentId);
    }
}
```