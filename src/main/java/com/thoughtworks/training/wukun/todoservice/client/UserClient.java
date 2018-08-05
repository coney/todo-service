package com.thoughtworks.training.wukun.todoservice.client;


import com.thoughtworks.training.wukun.todoservice.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user",url = "http://localhost:8080")
public interface UserClient {
    @PostMapping("/api/verifications")
    User verifyToken(String token);
}
