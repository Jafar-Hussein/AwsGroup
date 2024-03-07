package com.example.CompanyEmployee.repository;

import com.example.CompanyEmployee.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RoleRepository interface extends JpaRepository
 * Detta repository används för att hantera Role-entiteten
 * Den innehåller metoder för att hämta data från databasen
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Optional<Role> findByAuthority(String authority);


    Optional<Role> findById(Integer id);
}
