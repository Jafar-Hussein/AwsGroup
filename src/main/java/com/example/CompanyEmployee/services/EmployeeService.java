package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.models.Employee;
import com.example.CompanyEmployee.models.EmployeeDTO;
import com.example.CompanyEmployee.repository.CityRepository;
import com.example.CompanyEmployee.repository.CompanyRepository;
import com.example.CompanyEmployee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;


    public Employee getEmployeeByName(String employeeName) {
        return employeeRepository.findByFirstName(employeeName).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(employee.getEmployeeId());
            employeeDTO.setFirstName(employee.getFirstName());
            employeeDTO.setLastName(employee.getLastName());
            employeeDTO.setJobTitle(employee.getJobTitle());
            employeeDTO.setSalary(employee.getSalary());
            employeeDTO.setCity(employee.getCity());
            employeeDTO.setCompany(employee.getCompany());
            // Add more mappings if needed

            employeeDTOs.add(employeeDTO);
        }
        return employeeDTOs;
    }




    public ResponseEntity<?> addEmployee(Employee employee) {
        // Check if the city already exists
        City existingCity = cityRepository.findByCityName(employee.getCity().getCityName());
        if (existingCity == null) {
            // If the city doesn't exist, create a new one
            existingCity = cityRepository.save(employee.getCity());
        }

        // Associate the city with the employee
        employee.setCity(existingCity);

        // Check if the company already exists
        Optional<Company> optionalExistingCompany = companyRepository.findByCompanyName(employee.getCompany().getCompanyName());
        if (optionalExistingCompany.isEmpty()) {
            // If the company doesn't exist, create a new one
            Company existingCompany = companyRepository.save(employee.getCompany());
            // Associate the company with the employee
            employee.setCompany(existingCompany);
        } else {
            // If the company exists, use it
            Company existingCompany = optionalExistingCompany.get();
            // Associate the company with the employee
            employee.setCompany(existingCompany);
        }

        // Save the employee
        employeeRepository.save(employee);

        return ResponseEntity.ok("Employee added successfully");
    }


    public ResponseEntity<?> updateEmployee(Long id, EmployeeDTO employeeDto) {
        if (employeeRepository.existsById(id)) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                employee.setFirstName(employeeDto.getFirstName());
                employee.setLastName(employeeDto.getLastName());
                employee.setJobTitle(employeeDto.getJobTitle());
                employee.setSalary(employeeDto.getSalary());

                // You may need to set city and company here as well, depending on your requirements

                employeeRepository.save(employee);
                return ResponseEntity.ok("Employee updated successfully");
            }
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
