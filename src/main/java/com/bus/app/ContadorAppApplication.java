package com.bus.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ContadorAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContadorAppApplication.class, args);
    }

}
