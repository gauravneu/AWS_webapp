package com.example.awscloudproject.controller;

import com.example.awscloudproject.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @GetMapping("/hello")
    public String hello() {
        return demoService.sayHello();
    }

    @GetMapping("/WithoutAuth")
    public String helloWithoutAuth() {
        return demoService.sayHelloWithoutAuth();
    }
}
