package com.flip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.flip"})
public class FlipApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlipApiApplication.class, args);
    }

}
