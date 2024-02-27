package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public String getCompanyByName(String companyName) {
        return companyRepository.findByCompanyName(companyName).orElseThrow(() -> new RuntimeException("Company not found")).getCompanyName();
    }

    public ResponseEntity<?> getAllCompanies() {
        if (companyRepository.findAll().isEmpty()) {
            return ResponseEntity.badRequest().body("No companies found");
        }
        return ResponseEntity.ok(companyRepository.findAll());
    }

    public ResponseEntity<?> addCompany(Company company) {
        if (companyRepository.findByCompanyName(company.getCompanyName()).isPresent()) {
            return ResponseEntity.badRequest().body("Company already exists");
        }
        companyRepository.save(company);
        return ResponseEntity.ok("Company added successfully");
    }

    public ResponseEntity<?> updateCompany(Long id, Company company) {
        if (companyRepository.existsById(id)) {
            company.setId(id);
            companyRepository.save(company);
            return ResponseEntity.ok("Company updated successfully");
        }
        return ResponseEntity.badRequest().body("Company not found");
    }

    public ResponseEntity<?> deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return ResponseEntity.ok("Company deleted successfully");
        }
        return ResponseEntity.badRequest().body("Company not found");
    }


}

