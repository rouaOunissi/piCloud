package com.pi.projet.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-url",url = "${application.config.user-url}")
public interface User {

    @GetMapping("/mon-profil")
    public Long monProfil();
}
