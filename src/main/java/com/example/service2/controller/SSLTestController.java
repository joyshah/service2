package com.example.service2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SSLTestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/withTrustStore")
    public ResponseEntity<String> getDataFromService1OverTLS() {
        return restTemplate.getForEntity("https://localhost:8081", String.class);
    }

    @GetMapping("/withoutTrustStore")
    public String getDataFromService1WithoutTrustStore() {
        RestTemplate restTemplateWithoutTrustStore = new RestTemplate();
        try {
            return restTemplateWithoutTrustStore.getForEntity("https://localhost:8081", String.class).toString();
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
