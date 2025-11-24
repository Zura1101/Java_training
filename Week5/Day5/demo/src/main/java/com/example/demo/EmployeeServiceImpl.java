package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // Simple in-memory store instead of real DB
    private Map<Integer, Employee> employeeStore = new HashMap<>();

    @Override
    public Employee addEmployee(Employee emp) {
        employeeStore.put(emp.getId(), emp);
        return emp;
    }

    @Override
    public Employee updateEmployee(Integer id, Employee emp) {
        if (!employeeStore.containsKey(id)) {
            return null;
        }
        emp.setId(id); // ensure path ID used
        employeeStore.put(id, emp);
        return emp;
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        if (!employeeStore.containsKey(id)) {
            return false;
        }
        employeeStore.remove(id);
        return true;
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeStore.get(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeStore.values());
    }
}
