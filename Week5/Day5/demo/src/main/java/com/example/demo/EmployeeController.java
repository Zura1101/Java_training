package com.example.demo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    // Constructor injection
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // CREATE – POST /addEmployee
    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody Employee emp,
                                         BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Employee saved = employeeService.addEmployee(emp);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Employee added successfully: " + saved.getName());
    }

    // READ ONE – GET /employee/{id}
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Integer id) {
        Employee emp = employeeService.getEmployeeById(id);

        if (emp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with id " + id + " not found");
        }

        return ResponseEntity.ok(emp);
    }

    // READ ALL – GET /employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // UPDATE – PUT /updateEmployee/{id}
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id,
                                            @Valid @RequestBody Employee emp,
                                            BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Employee updated = employeeService.updateEmployee(id, emp);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with id " + id + " not found");
        }

        return ResponseEntity.ok(
                "Employee updated successfully: " + updated.getName()
                        + ", id=" + updated.getId()
                        + ", salary=" + updated.getSalary()
        );
    }

    // DELETE – DELETE /deleteEmployee/{id}
    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {

        boolean deleted = employeeService.deleteEmployee(id);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with id " + id + " not found");
        }

        return ResponseEntity.ok("Employee with id " + id + " deleted successfully");
    }
}
