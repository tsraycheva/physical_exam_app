package com.example.physical_exam.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Physical Exam Application", version = "1"),
        servers = {@Server(url = "http://localhost:8080")},
        tags = {@Tag(name = "employee", description = "Employees that are in the list to be tested."),
                @Tag(name = "exercise", description = "The exercises with requirements that must be fulfilled."),
                @Tag(name = "result", description = "The results from the exams performed by employees.")}
)
public class OpenApiConfiguration {
}
