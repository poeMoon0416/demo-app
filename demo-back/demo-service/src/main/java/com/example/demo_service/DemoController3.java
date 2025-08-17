package com.example.demo_service;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// @RestControllerが複数でもok(分割可能)
@RestController
public class DemoController3 {
    @GetMapping("/rest-greet")
    public String getRestGreet(@RequestParam("name") String name) {
        return String.format("hello, %s!", name);
    }
}
