package com.beesocial.authenticationserver.feign;

import com.beesocial.authenticationserver.DTOs.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "firebase-storage-service")
public interface FirebaseStorageClient {

    @GetMapping("/api/firebase/user/{email}")
    User getUserByEmail(@PathVariable("email") String email);

    @PostMapping("/api/firebase/users")
    <T> String saveUser(@RequestBody T user);
}
