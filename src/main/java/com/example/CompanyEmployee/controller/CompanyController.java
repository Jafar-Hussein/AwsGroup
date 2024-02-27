package com.example.CompanyEmployee.controller;

import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    @GetMapping("/getByName")
   public ResponseEntity<String> getCompanyByName(@RequestBody String companyName) {
        String company = companyService.getCompanyByName(companyName);
        return ResponseEntity.ok(company);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return companyService.updateCompany(id, company);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }

}
