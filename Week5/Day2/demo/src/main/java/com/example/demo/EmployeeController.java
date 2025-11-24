package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RestController
public class EmployeeController {

    @PostMapping("/addEmployee")
    public Object addEmployee(@Valid @RequestBody Employee emp, BindingResult result) {

        if (result.hasErrors()) {
            // return validation errors as response
            return result.getAllErrors();
        }

        return "Employee added successfully: " + emp.getName();
    }
}
