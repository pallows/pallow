package com.pallow.pallow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PallowApplication {

    public static void main(String[] args) {
        SpringApplication.run(PallowApplication.class, args);
    }

}
