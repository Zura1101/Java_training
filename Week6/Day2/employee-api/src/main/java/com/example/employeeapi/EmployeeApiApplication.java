package com.example.employeeapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.service.EmployeeService;

@SpringBootApplication
public class EmployeeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner testEmployeeService(EmployeeService employeeService) {
        return args -> {
            System.out.println("=== Testing EmployeeService & Repository ===");

            Employee e1 = new Employee("Bhargav", "IT", 80000.0);
            Employee e2 = new Employee("Reddy", "HR", 60000.0);

            employeeService.addEmployee(e1);
            employeeService.addEmployee(e2);

            System.out.println("All employees:");
            employeeService.getAllEmployees()
                    .forEach(System.out::println);

            System.out.println("=== End of test ===");
        };
    }
}
