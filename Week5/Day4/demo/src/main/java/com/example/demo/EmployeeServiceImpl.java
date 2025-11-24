package com.example.demo;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    // simple in-memory "database"
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
        emp.setId(id);
        employeeStore.put(id, emp);
        return emp;
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        if (!employeeStore.containsKey(id)) {
            return false;  // not found
        }
        employeeStore.remove(id);
        return true;       // deleted
    }
}
