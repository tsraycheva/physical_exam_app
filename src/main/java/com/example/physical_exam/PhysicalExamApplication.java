package com.example.physical_exam;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(
        info = @Info(title = "Physical Exam Application", version = "1"),
        servers = {@Server(url = "http://localhost:8080")},
        tags = {@Tag(name = "employee", description = "Employees that are in the list to be tested."),
                @Tag(name = "exercise", description = "The exercises with requirements that must be fulfilled."),
                @Tag(name = "result", description = "The results from the exams performed by employees.")}
)
public class PhysicalExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhysicalExamApplication.class, args);
    }

}
