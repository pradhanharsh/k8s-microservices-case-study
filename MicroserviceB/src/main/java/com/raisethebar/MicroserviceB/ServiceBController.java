package com.raisethebar.MicroserviceB;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ServiceBController {

    private final AtomicInteger counter = new AtomicInteger(0);

    @GetMapping("/hello")
    public String hello() {
        int count = counter.incrementAndGet();
        return "Hello from Service B! Request count: " + count;
    }
}
