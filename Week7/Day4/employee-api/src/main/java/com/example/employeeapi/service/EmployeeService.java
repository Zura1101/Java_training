package com.example.employeeapi.service;

import java.util.List;

import com.example.employeeapi.entity.Employee;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    Employee getEmployeeById(Integer id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Integer id, Employee updatedEmployee);
    void deleteEmployee(Integer id);
}
