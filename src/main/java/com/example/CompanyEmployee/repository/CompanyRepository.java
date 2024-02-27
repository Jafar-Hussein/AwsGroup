package com.example.CompanyEmployee.repository;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
