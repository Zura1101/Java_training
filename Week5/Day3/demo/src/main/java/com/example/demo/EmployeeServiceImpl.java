package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // simple in-memory store instead of DB
    private Map<Integer, Employee> employeeStore = new HashMap<>();

    @Override
    public Employee addEmployee(Employee emp) {
        employeeStore.put(emp.getId(), emp);
        return emp;
    }

    @Override
    public Employee updateEmployee(Integer id, Employee emp) {
        if (!employeeStore.containsKey(id)) {
            return null; // employee not found
        }

        // ensure id from path is used
        emp.setId(id);
        employeeStore.put(id, emp);
        return emp;
    }
}
