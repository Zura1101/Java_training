package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    // constructor injection
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/addEmployee")
    public Object addEmployee(@Valid @RequestBody Employee emp, BindingResult result) {

        if (result.hasErrors()) {
            return result.getAllErrors();
        }

        employeeService.addEmployee(emp);
        return "Employee added successfully: " + emp.getName();
    }

    @PutMapping("/updateEmployee/{id}")
    public Object updateEmployee(@PathVariable Integer id,
                                 @Valid @RequestBody Employee emp,
                                 BindingResult result) {

        if (result.hasErrors()) {
            return result.getAllErrors();
        }

        Employee updated = employeeService.updateEmployee(id, emp);

        if (updated == null) {
            return "Employee with id " + id + " not found";
        }

        return "Employee updated successfully: " + updated.getName()
                + ", id=" + updated.getId()
                + ", salary=" + updated.getSalary();
                                 }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {

        boolean deleted = employeeService.deleteEmployee(id);

        if (!deleted) {
        // 👇 Proper exception-style response: 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Employee with id " + id + " not found");
        }

        return ResponseEntity.ok("Employee with id " + id + " deleted successfully");
    }

    
}
