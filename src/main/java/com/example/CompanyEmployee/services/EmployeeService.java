package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.Employee;
import com.example.CompanyEmployee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @Author Jafar Hussein
 * @Class EmployeeService
 * @Description: Denna klassen är en serviceklass för anställda, den kopplar ihop EmployeeRepository med EmployeeController.
 * @method getEmployeeByName hämtar anställda med hjälp av anställdasnamn.
 * @method addEmployee lägger till en anställd i databasen.
 * @method getEmployeesByCurrentCompany hämtar alla anställda i databasen, använder den inloggade användaren och hämtar anställda från det företaget.
 * @method updateEmployee uppdaterar en sparad anställd i databasen med hjälp av id.
 * @method deleteEmployee raderar en sparad anställd i databasen med hjälp av id.
 * */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    public String getEmployeeByName(String employeeName) {
        return employeeRepository.findByFirstName(employeeName).orElseThrow(() -> new RuntimeException("Employee not found")).getFirstName();
    }

    public ResponseEntity<?> getEmployeesByCurrentCompany() {
        // Get the current user's company
        String currentCompanyName = userService.getCurrentUser().getCompany().getCompanyName();

        // Get employees of the current company
        List<Employee> employees = employeeRepository.findByCompanyCompanyName(currentCompanyName);

        if (employees.isEmpty()) {
            return ResponseEntity.badRequest().body("No employees found for the current company");
        }

        return ResponseEntity.ok(employees);
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
            employee.setEmployeeId(id);
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
