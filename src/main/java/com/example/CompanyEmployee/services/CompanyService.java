package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.models.User;
import com.example.CompanyEmployee.repository.CityRepository;
import com.example.CompanyEmployee.repository.CompanyRepository;
import com.example.CompanyEmployee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
    private final CityRepository cityRepository;

    public Company getCompanyByName(String companyName) {
        return companyRepository.findByCompanyName(companyName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));
    }


    //get by id
    public ResponseEntity<?> getCompanyById(Long id) {
        if (companyRepository.findCompanyByCompanyId(id).isPresent()) {
            return ResponseEntity.ok(companyRepository.findCompanyByCompanyId(id));
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
        // Kontrollera om företaget redan finns
        if (companyRepository.findByCompanyName(company.getCompanyName()).isPresent()) {
            return ResponseEntity.badRequest().body("Company already exists");
        }

        // Kontrollera om staden redan finns
        City existingCity = cityRepository.findByCityName(company.getCity().getCityName());
        if (existingCity != null) {
            // Om staden redan finns, sätt den i företaget
            company.setCity(existingCity);
        } else {
            // Om staden inte finns, returnera felmeddelande
            return ResponseEntity.badRequest().body("City not found");
        }

        // Spara företaget
        companyRepository.save(company);

        return ResponseEntity.ok("Company added successfully");
    }






    public ResponseEntity<?> updateCompany(Long id, Company company) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            Company existingCompany = optionalCompany.get();
            // Kontrollera om staden i företagsobjektet finns i databasen
            City existingCity = cityRepository.findByCityName(company.getCity().getCityName());
            if (existingCity == null) {
                // Om staden inte finns, skapa en ny
                existingCity = cityRepository.save(company.getCity());
            }
            // Ange den befintliga staden för företaget
            existingCompany.setCity(existingCity);

            // Uppdatera andra fält i företaget om det behövs
            existingCompany.setCompanyName(company.getCompanyName());

            // Spara det uppdaterade företaget
            companyRepository.save(existingCompany);
            return ResponseEntity.ok("Company updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Company not found");
        }
    }



    public ResponseEntity<?> deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return ResponseEntity.ok("Company deleted successfully");
        }
        return ResponseEntity.badRequest().body("Company not found");
    }


}

