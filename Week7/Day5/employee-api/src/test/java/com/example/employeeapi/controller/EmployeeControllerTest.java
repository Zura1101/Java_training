package com.example.employeeapi.controller;

import com.example.employeeapi.config.SecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import com.example.employeeapi.dto.EmployeeRequestDTO;
import com.example.employeeapi.dto.EmployeeResponseDTO;
import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@Import(SecurityConfig.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService; // mocked service

    @Autowired
    private ObjectMapper objectMapper;

    // helper for mapping entity -> response DTO (mimic your controller logic)
    private EmployeeResponseDTO toResponse(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDepartment(employee.getDepartment());
        dto.setSalary(employee.getSalary());
        return dto;
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    void addEmployee_shouldReturnCreatedEmployee() throws Exception {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("Bhargava");
        requestDTO.setDepartment("IT");
        requestDTO.setSalary(100000.0);

        Employee saved = new Employee("Bhargava", "IT", 100000.0);
        saved.setId(1);

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(saved);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bhargava"));
    }

    @Test
    void getEmployeeById_whenExists_shouldReturnEmployee() throws Exception {
        Employee emp = new Employee("Bhargava", "IT", 100000.0);
        emp.setId(1);

        when(employeeService.getEmployeeById(1)).thenReturn(emp);

        mockMvc.perform(get("/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bhargava"));
    }

    @Test
    void getEmployeeById_whenNotExists_shouldReturn404() throws Exception {
        when(employeeService.getEmployeeById(99)).thenThrow(new EmployeeNotFoundException(99));

        mockMvc.perform(get("/employees/{id}", 99))
                .andExpect(status().isNotFound())
                // adjust field if your GlobalExceptionHandler returns different JSON
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void getAllEmployees_shouldReturnList() throws Exception {
        Employee emp1 = new Employee("Emp1", "IT", 90000.0);
        emp1.setId(1);
        Employee emp2 = new Employee("Emp2", "HR", 85000.0);
        emp2.setId(2);

        when(employeeService.getAllEmployees()).thenReturn(List.of(emp1, emp2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Emp1"))
                .andExpect(jsonPath("$[1].name").value("Emp2"));
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setDepartment("Sales");
        requestDTO.setSalary(120000.0);

        Employee updated = new Employee("Updated Name", "Sales", 120000.0);
        updated.setId(1);

        when(employeeService.updateEmployee(eq(1), any(Employee.class))).thenReturn(updated);

        mockMvc.perform(put("/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    void deleteEmployee_shouldReturnNoContent() throws Exception {
        // service no longer returns boolean; it throws if not found
        doNothing().when(employeeService).deleteEmployee(1);

        mockMvc.perform(delete("/employees/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    void deleteEmployee_whenNotExists_shouldReturn404() throws Exception {
        doThrow(new EmployeeNotFoundException(99))
                .when(employeeService).deleteEmployee(99);

        mockMvc.perform(delete("/employees/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }


    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    void addEmployee_whenValidationFails_shouldReturn400() throws Exception {
        EmployeeRequestDTO invalidRequest = new EmployeeRequestDTO();
        invalidRequest.setName(""); // @NotBlank violation
        invalidRequest.setDepartment("IT");
        invalidRequest.setSalary(100000.0);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
