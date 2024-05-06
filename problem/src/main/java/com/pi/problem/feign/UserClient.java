package com.pi.problem.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "USERS",url = "http://localhost:8010/api/v1/users")
public interface UserClient {

    @GetMapping("/auth/findEmail/{id}")
    ResponseEntity<String> findEmailById(@PathVariable("id") Long id);
    @PutMapping("/user/updateStatus/{id}")
     void setUserStatus(@PathVariable long id_user);
}