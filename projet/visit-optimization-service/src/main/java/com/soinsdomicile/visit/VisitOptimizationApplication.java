package com.soinsdomicile.visit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VisitOptimizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitOptimizationApplication.class, args);
    }
}
