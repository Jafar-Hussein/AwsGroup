package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
/**
 * @Author Jafar
 * @Class CompanyService
 * @Description: Denna klassen är en serviceklass för företag, den kopplar ihop CompanyRepository med CompanyController så att vi kan arbeta med databasen på ett smidigt sätt.
 * @method getCompanyByName hämtar företag med hjälp av företagsnamn.
 * @method getAllCompanies hämtar alla företag i databasen.
 * @method addCompany lägger till ett företag i databasen.
 * @method updateCompany uppdaterar ett företag i databasen med hjälp av id.
 * @method deleteCompany raderar ett företag i databasen med hjälp av id.
 * @method getCompanyById hämtar företag med hjälp av id.
 * */
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public String getCompanyByName(String companyName) {
        return companyRepository.findByCompanyName(companyName).orElseThrow(() -> new RuntimeException("Company not found")).getCompanyName();
    }

    //get by id
    public ResponseEntity<?> getCompanyById(Long id) {
        if (companyRepository.findCompanyById(id).isPresent()) {
            return ResponseEntity.ok(companyRepository.findCompanyById(id));
        }
        return ResponseEntity.badRequest().body("Company not found");
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

