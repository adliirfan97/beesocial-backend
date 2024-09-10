# Eureka Server

[Netflix Eureka Guide](https://spring.io/guides/gs/service-registration-and-discovery)

Set up a Netflix Eureka Service Registry, `eureka-server` , and then two other services that will register with the Eureka Server. One of the services will call the other service using Web Client.

Generate the spring project with [Spring Initializr](https://start.spring.io/):

- [Eureka Server](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.example&artifactId=eureka-server&name=eureka-server&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.eurekaserver&dependencies=cloud-eureka-server)
- [User Management Service](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.beesocial&artifactId=user-management-service&name=user-management-service&description=Demo%20project%20for%20Spring%20Boot&packageName=com.beesocial.usermanagementservice&dependencies=devtools,lombok,web,webflux,cloud-eureka,actuator)
- [Event Management Service](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.beesocial&artifactId=event-management-service&name=event-management-service&description=Demo%20project%20for%20Spring%20Boot&packageName=com.beesocial.eventmanagementservice&dependencies=devtools,lombok,web,webflux,cloud-eureka,actuator)

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

Now that you have started a service registry, you can stand up clients that interact with the registry. Our applications, User Management Service and Event Management Service, automatically register with the Eureka server because we have `spring-cloud-starter-netflix-eureka-client` on the classpath. To avoid port conflicts, set the `server.port` parameter in both User Management Service and Event Management Service:

User Management Service:

```java
spring.application.name=user-management-service
server.port=8081
```

User Management Service:

```java
spring.application.name=user-management-service
server.port=8082
```

You can now see that the services are up in the [Eureka Dashboard](http://localhost:8761/).

# API Gateway

[Spring Cloud Gateway Guide](https://spring.io/projects/spring-cloud-gateway)

API Gateway manages routing, filtering, and proxying in microservice architectures. It helps address challenges such as API evolution, request routing, and legacy system integration. Additionally, API Gateway can reroute requests and modify headers for microservices.

Generate the spring project with [Spring Initializr](https://start.spring.io/):

- [API Gateway](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=22&groupId=com.example&artifactId=api-gateway&name=api-gateway&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.apigateway&dependencies=actuator,cloud-eureka)

## Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

## Re-route Instructions

```java

```