package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.*;
import com.example.CompanyEmployee.repository.CityRepository;
import com.example.CompanyEmployee.repository.CompanyRepository;
import com.example.CompanyEmployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Den här klassen innehåller enhetstester för EmployeeService-klassen.
 * Vi använder Mockito för att mocka EmployeeRepository och UserService.
 * Metoderna i EmployeeService-klassen testas för att säkerställa att de returnerar förväntade värden.
 * @Mock: Skapar en mock för EmployeeRepository och UserService.
 * @BeforeEach: Annotering som anger att MockitoAnnotations.initMocks(this) ska köras innan varje testmetod.
 * @Author Clara Brorson
 */
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserService userService;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEmployeeByName() {
        String expectedEmployeeName = "Employee";
        Employee employee = new Employee();
        employee.setFirstName(expectedEmployeeName);
        when(employeeRepository.findByFirstName(expectedEmployeeName)).thenReturn(Optional.of(employee));

        String actualEmployeeName = employeeService.getEmployeeByName(expectedEmployeeName).getFirstName();

        assertEquals(expectedEmployeeName, actualEmployeeName);
    }

    @Test
    void getAllEmployees() {
        // Mocking the EmployeeRepository
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(new Employee()));

        // Creating a mock Company and User
        Company company = new Company();
        company.setCompanyName("Example Company");
        User user = new User();
        user.setCompany(company);

        // Mocking the UserService to return the mock User
        when(userService.getCurrentUser()).thenReturn(user);

        // Calling the getAllEmployees method of the EmployeeService
        List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployees();

        // Verifying that the returned list is not null and has one item
        assertNotNull(employeeDTOs);
        assertEquals(1, employeeDTOs.size());

    }

    @Test
    void addEmployee() {
        // Mock City object
        City city = new City();
        city.setCityName("Test City");

        // Mock Company object
        Company company = new Company();
        company.setCompanyName("Test Company");

        // Mock the behavior of CityRepository
        when(cityRepository.findByCityName(any())).thenReturn(city);

        // Mock the behavior of CompanyRepository
        when(companyRepository.findByCompanyName(any())).thenReturn(Optional.of(company));

        // Create an Employee object with associated City and Company
        Employee employee = new Employee();
        employee.setCity(city);
        employee.setCompany(company);

        // Mock behavior of EmployeeRepository
        when(employeeRepository.findByFirstName(any())).thenReturn(Optional.empty());

        // Call the method under test
        ResponseEntity<?> response = employeeService.addEmployee(employee);

        // Assertions
        assertEquals(ResponseEntity.ok("Employee added successfully"), response);
        verify(employeeRepository, times(1)).save(employee);
    }


    @Test
    void updateEmployee() {
        Long id = 1L;
        when(employeeRepository.existsById(id)).thenReturn(false); // Set existsById to return false

        ResponseEntity<?> response = employeeService.updateEmployee(id, new EmployeeDTO());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Employee not found", response.getBody());
        verify(employeeRepository, never()).save(any()); // Ensure save method is never called
    }


    @Test
    void deleteEmployee() {
        Long id = 1L;
        when(employeeRepository.existsById(id)).thenReturn(true);

        ResponseEntity<?> response = employeeService.deleteEmployee(id);

        assertEquals(ResponseEntity.ok("Employee deleted successfully"), response);
        verify(employeeRepository, times(1)).deleteById(id);
    }
}