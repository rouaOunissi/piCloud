package com.pi.projet.FeignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

//@FeignClient(name = "user-url",url = "${application.config.user-url}", configuration = FeignClientConfig.class)
@FeignClient(name = "user-url",url = "${application.config.user-url}")
public interface User {

    @GetMapping("/mon-profil")
    ResponseEntity<UserProfile> getProfile(@RequestHeader("Authorization") String bearerToken);
}
