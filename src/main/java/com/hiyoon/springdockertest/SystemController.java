package com.hiyoon.springdockertest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system")
public class SystemController {

    @GetMapping("/health")
    public String checkHealth() {
        return "OK";
    }
}
