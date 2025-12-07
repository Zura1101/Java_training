package com.example.employeeapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.employeeapi.entity.Employee;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1);
        employee.setName("Bhargava");
        employee.setDepartment("IT");
        employee.setSalary(100000.0);
    }

    // -------- addEmployee --------

    @Test
    void addEmployee_shouldSaveAndReturnEmployee() {
        // arrange
        when(employeeRepository.save(employee)).thenReturn(employee);

        // act
        Employee result = employeeService.addEmployee(employee);

        // assert
        assertNotNull(result);
        assertEquals("Bhargava", result.getName());
        assertEquals("IT", result.getDepartment());
        assertEquals(100000.0, result.getSalary());
        verify(employeeRepository, times(1)).save(employee);
    }

    // -------- getEmployeeById --------

    @Test
    void getEmployeeById_whenEmployeeExists_shouldReturnEmployee() {
        // arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        // act
        Employee result = employeeService.getEmployeeById(1);

        // assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Bhargava", result.getName());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void getEmployeeById_whenEmployeeDoesNotExist_shouldThrowException() {
        // arrange
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(99));

        verify(employeeRepository, times(1)).findById(99);
    }

    // -------- getAllEmployees --------
    @Test
    void getAllEmployees_whenNoEmployees_shouldReturnEmptyList() {
        when(employeeRepository.findAll()).thenReturn(List.of());

        List<Employee> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }


    @Test
    void getAllEmployees_shouldReturnListOfEmployees() {
        // arrange
        Employee emp2 = new Employee("Test2", "HR", 90000.0);
        emp2.setId(2);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee, emp2));

        // act
        List<Employee> result = employeeService.getAllEmployees();

        // assert
        assertEquals(2, result.size());
        assertEquals("Bhargava", result.get(0).getName());
        assertEquals("Test2", result.get(1).getName());
        verify(employeeRepository, times(1)).findAll();
    }

    // -------- updateEmployee --------

    @Test
    void updateEmployee_whenEmployeeExists_shouldUpdateAndReturnEmployee() {
        // arrange
        Employee updated = new Employee();
        updated.setName("Updated Name");
        updated.setDepartment("HR");
        updated.setSalary(120000.0);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        Employee result = employeeService.updateEmployee(1, updated);

        // assert
        assertEquals(1, result.getId());
        assertEquals("Updated Name", result.getName());
        assertEquals("HR", result.getDepartment());
        assertEquals(120000.0, result.getSalary());

        // verify we saved the updated entity with same ID
        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        Employee saved = captor.getValue();
        assertEquals(1, saved.getId());
        assertEquals("Updated Name", saved.getName());
    }
    @Test
    void updateEmployee_shouldCallFindByIdAndSaveOnce() {
        Employee updated = new Employee();
        updated.setName("Updated");
        updated.setDepartment("Sales");
        updated.setSalary(150000.0);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        employeeService.updateEmployee(1, updated);

        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }


    @Test
    void updateEmployee_whenEmployeeDoesNotExist_shouldThrowException() {
        // arrange
        Employee updated = new Employee();
        updated.setName("Doesn't matter");

        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.updateEmployee(99, updated));

        verify(employeeRepository, times(1)).findById(99);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // -------- deleteEmployee --------

    @Test
    void deleteEmployee_whenEmployeeExists_shouldDelete() {
        // arrange
        when(employeeRepository.existsById(1)).thenReturn(true);

        // act
        employeeService.deleteEmployee(1);

        // assert
        verify(employeeRepository, times(1)).existsById(1);
        verify(employeeRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteEmployee_whenEmployeeDoesNotExist_shouldThrowException() {
        // arrange
        when(employeeRepository.existsById(99)).thenReturn(false);

        // act + assert
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployee(99));

        verify(employeeRepository, times(1)).existsById(99);
        verify(employeeRepository, never()).deleteById(anyInt());
    }
    @Test
    void deleteEmployee_whenNotExists_shouldNotCallDeleteById() {
        when(employeeRepository.existsById(123)).thenReturn(false);

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployee(123));

        verify(employeeRepository, times(1)).existsById(123);
        verify(employeeRepository, never()).deleteById(anyInt());
    }
}
