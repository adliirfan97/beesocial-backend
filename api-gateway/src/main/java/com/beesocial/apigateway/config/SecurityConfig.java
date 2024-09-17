//package com.beesocial.apigateway.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityConfigurerAdapter;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors()  // Enable CORS
//                .and()
//                .csrf().disable() // Disable CSRF for non-browser clients
//                .authorizeRequests()
//                .anyRequest().authenticated();
//    }
//}
//
