package com.example.demo;

import java.util.List;

public interface EmployeeService {

    Employee addEmployee(Employee emp);

    Employee updateEmployee(Integer id, Employee emp);

    boolean deleteEmployee(Integer id);

    Employee getEmployeeById(Integer id);

    List<Employee> getAllEmployees();
}
