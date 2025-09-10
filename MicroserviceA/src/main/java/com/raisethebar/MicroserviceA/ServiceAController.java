package com.raisethebar.MicroserviceA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceAController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/callB")
    public String callServiceB() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://microserviceb-service:8081/hello", String.class);
        return "Response from Service B: " + response;
    }
}
