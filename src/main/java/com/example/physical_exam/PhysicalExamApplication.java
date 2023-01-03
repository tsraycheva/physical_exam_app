package com.example.physical_exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PhysicalExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhysicalExamApplication.class, args);
    }

}
