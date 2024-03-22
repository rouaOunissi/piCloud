package com.pi.cours;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public  class TestController {

    @GetMapping("/aaa")
    public String test() {
        return "test";
    }
}