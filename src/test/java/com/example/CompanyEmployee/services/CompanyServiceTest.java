package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.models.User;
import com.example.CompanyEmployee.repository.CityRepository;
import com.example.CompanyEmployee.repository.CompanyRepository;
import com.example.CompanyEmployee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CityRepository cityRepository;
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        companyService = new CompanyService(companyRepository, cityRepository);
    }

    @Test
    void getCompanyByName() {
        // Arrange
        String expectedCompanyName = "Company";
        Company company = new Company();
        company.setCompanyName(expectedCompanyName);
        when(companyRepository.findByCompanyName(expectedCompanyName)).thenReturn(Optional.of(company));

        // Act
        Company actualCompany = companyService.getCompanyByName(expectedCompanyName);

        // Assert
        assertEquals(expectedCompanyName, actualCompany.getCompanyName());
    }

    @Test
    void getAllCompanies() {
        // Arrange
        when(companyRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        // Act
        ResponseEntity<?> response = companyService.getAllCompanies();

        // Assert
        assertEquals(ResponseEntity.badRequest().body("No companies found"), response);
    }

    @Test
    void addCompany() {
        // Arrange
        Company company = new Company();
        company.setCompanyName("New Company");
        City city = new City("Stockholm"); // Assuming city is set
        company.setCity(city); // Set the city for the company

        when(companyRepository.findByCompanyName(any())).thenReturn(Optional.empty());
        when(cityRepository.findByCityName(any())).thenReturn(city);
        when(companyRepository.save(any())).thenReturn(company);

        // Act
        ResponseEntity<?> response = companyService.addCompany(company);

        // Assert
        assertEquals(ResponseEntity.ok("Company added successfully"), response);
        verify(companyRepository, times(1)).save(company);
    }


    @Test
    void updateCompany() {
        // Arrange
        Long id = 1L;
        Company company = new Company();
        company.setCompanyId(id);
        City city = new City("Stockholm"); // Set a non-null city object
        company.setCity(city);

        when(companyRepository.findById(id)).thenReturn(Optional.of(company));
        when(cityRepository.findByCityName(any())).thenReturn(city);
        when(companyRepository.save(any())).thenReturn(company);

        // Act
        ResponseEntity<?> response = companyService.updateCompany(id, company);

        // Assert
        assertEquals(ResponseEntity.ok("Company updated successfully"), response);
        verify(companyRepository, times(1)).save(company);
    }


    @Test
    void deleteCompany() {
        // Arrange
        Long id = 1L;

        when(companyRepository.existsById(id)).thenReturn(true);

        // Act
        ResponseEntity<?> response = companyService.deleteCompany(id);

        // Assert
        assertEquals(ResponseEntity.ok("Company deleted successfully"), response);
        verify(companyRepository, times(1)).deleteById(id);
    }
}