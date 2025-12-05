package com.example.employeeapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee updateEmployee(Integer id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedEmployee.getName());
                    existing.setDepartment(updatedEmployee.getDepartment());
                    existing.setSalary(updatedEmployee.getSalary());
                    return employeeRepository.save(existing);
                })
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public void deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    

    

    

}
