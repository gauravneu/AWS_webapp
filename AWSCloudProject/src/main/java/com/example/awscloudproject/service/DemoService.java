package com.example.awscloudproject.service;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public String sayHello() {
        return "hello from service";
    }

    public String sayHelloWithoutAuth() {
        return "hello without auth";
    }

}
