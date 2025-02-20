package com.example.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerController {


    @PostMapping
    public void createCustomer() {
        // throw new RuntimeException("some error");
        log.debug("Customer");
    }

    @GetMapping
    public void getCustomer() {
        // throw new RuntimeException("some error");
        log.debug("Customer ==");
    }
}
