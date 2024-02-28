package com.example.CompanyEmployee.controller;

import com.example.CompanyEmployee.models.Employee;
import com.example.CompanyEmployee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @Author Jafar Hussein
 * @url http://localhost:8080/employee
 * @About denna klass har crud-metoder för skapa, läsa, uppdatera och radera anställda, både admin och user(företaget) kan använda dessa metoder,
 * @method getEmployeeByName hämtar anställda med hjälp av anställdasnamn.
 * @method addEmployee lägger till en anställd. både admin och user(företaget) kan använda denna metod.
 * @method getAllEmployees hämtar alla anställda. admin kan använda denna metod.
 * @method updateEmployee uppdaterar en anställd med hjälp av id.
 * @method deleteEmployee raderar en anställd med hjälp av id.
 * */
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
        return employeeService.getEmployeesByCurrentCompany();
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
