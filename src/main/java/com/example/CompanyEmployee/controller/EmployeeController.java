package com.example.CompanyEmployee.controller;

import com.example.CompanyEmployee.models.Employee;
import com.example.CompanyEmployee.services.EmployeeService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("/getByName")
    public ResponseEntity<String> getEmployeeByName(@RequestBody String employeeName) {
        String employee = employeeService.getEmployeeByName(employeeName);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

}
