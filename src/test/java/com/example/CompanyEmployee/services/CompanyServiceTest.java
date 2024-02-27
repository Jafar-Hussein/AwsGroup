package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.repository.CompanyRepository;
import com.example.CompanyEmployee.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;
    private CompanyService companyService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        companyService = new CompanyService(companyRepository);
    }

    @Test
    void getCompanyByName() {
        // arrange
        String expectedCompanyName = "Company";
        Company company = new Company();
        company.setCompanyName(expectedCompanyName);
        when(companyRepository.findByCompanyName(expectedCompanyName)).thenReturn(Optional.of(company));
        // act
        String actualCompanyName = companyService.getCompanyByName(expectedCompanyName);
        // assert
        assertEquals(expectedCompanyName, actualCompanyName);
    }

    @Test
    void getAllCompanies() {
        // arrange
        when(companyRepository.findAll()).thenReturn(java.util.Collections.emptyList());
        // act
        ResponseEntity<?> response = companyService.getAllCompanies();
        // assert
        assertEquals(ResponseEntity.badRequest().body("No companies found"), response);
    }

    @Test
    void addCompany() {
        // arrange
        Company company = new Company();
        when(companyRepository.findByCompanyName(any())).thenReturn(Optional.empty());
        // act
        ResponseEntity<?> response = companyService.addCompany(company);
        // assert
        assertEquals(ResponseEntity.ok("Company added successfully"), response);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void updateCompany() {
        // arrange
        Long id = 1L;
        Company company = new Company();
        when(companyRepository.existsById(id)).thenReturn(true);
        // act
        ResponseEntity<?> response = companyService.updateCompany(id, company);
        // assert
        assertEquals(ResponseEntity.ok("Company updated successfully"), response);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void deleteCompany() {
        // arrange
        Long id = 1L;
        when(companyRepository.existsById(id)).thenReturn(true);
        // act
        ResponseEntity<?> response = companyService.deleteCompany(id);
        // assert
        assertEquals(ResponseEntity.ok("Company deleted successfully"), response);
        verify(companyRepository, times(1)).deleteById(id);
    }
}