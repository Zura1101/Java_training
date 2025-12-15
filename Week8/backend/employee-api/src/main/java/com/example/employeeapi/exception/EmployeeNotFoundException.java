package com.example.employeeapi.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Integer id) {
        super("Employee with id " + id + " not found");
    }
}
