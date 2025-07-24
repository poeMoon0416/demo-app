package com.example.demo_service_sub;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class DemoServiceSubController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public List<DemoServiceSubDto> getMethodName() {
        return List.of(new DemoServiceSubDto("World", "Hello World."),
                new DemoServiceSubDto("Spring", "Hello Spring"),
                new DemoServiceSubDto("Java", "Hello Java"));
    }

}
