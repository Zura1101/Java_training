package com.example.employeeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApiApplication.class, args);
    }

    //@Bean
    //public CommandLineRunner testEmployeeService(EmployeeService employeeService) {
    //    return args -> {
    //        System.out.println("=== Testing EmployeeService & Repository ===");
//
    //        Employee e1 = new Employee("Bhargav", "IT", 80000.0);
    //        Employee e2 = new Employee("Reddy", "HR", 60000.0);
//
    //        employeeService.addEmployee(e1);
    //        employeeService.addEmployee(e2);
//
    //        System.out.println("All employees:");
    //        employeeService.getAllEmployees()
    //                .forEach(System.out::println);
//
    //        System.out.println("=== End of test ===");
    //    };
    //}
}
