package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.Employee;
import com.example.CompanyEmployee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    public String getEmployeeByName(String employeeName) {
        return employeeRepository.findByFirstName(employeeName).orElseThrow(() -> new RuntimeException("Employee not found")).getFirstName();
    }

    public ResponseEntity<?> getAllEmployees() {
        if (employeeRepository.findAll().isEmpty()) {
            return ResponseEntity.badRequest().body("No employees found");
        }
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    public ResponseEntity<?> addEmployee(Employee employee) {
        if (employeeRepository.findByFirstName(employee.getFirstName()).isPresent()) {
            return ResponseEntity.badRequest().body("Employee already exists");
        }
        employeeRepository.save(employee);
        return ResponseEntity.ok("Employee added successfully");
    }

    public ResponseEntity<?> updateEmployee(Long id, Employee employee) {
        if (employeeRepository.existsById(id)) {
            employee.setId(id);
            employeeRepository.save(employee);
            return ResponseEntity.ok("Employee updated successfully");
        }
        return ResponseEntity.badRequest().body("Employee not found");
    }

    public ResponseEntity<?> deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok("Employee deleted successfully");
        }
        return ResponseEntity.badRequest().body("Employee not found");
    }
}
