package com.example.physical_exam.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.physical_exam.model.entity.Employee;

/**
 * Employee Controller with endpoints related to {@link Employee} Objects
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @GetMapping("/id")
    public String getEmployeeById(@RequestParam Integer id) {
        return "BRAVISIMO";
    }
}
