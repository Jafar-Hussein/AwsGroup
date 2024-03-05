package com.example.CompanyEmployee;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.models.Role;
import com.example.CompanyEmployee.models.User;
import com.example.CompanyEmployee.repository.CityRepository;
import com.example.CompanyEmployee.repository.CompanyRepository;
import com.example.CompanyEmployee.repository.RoleRepository;
import com.example.CompanyEmployee.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CompanyEmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyEmployeeApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, CompanyRepository companyRepository, CityRepository cityRepository) {
		return args -> {
			//Kontrollerar om en roll med auktoriteten "ADMIN" redan finns i databasen.
			// Om den finns, avslutas metoden tidigt och inget mer görs.
			if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

			//Skapar en ny Role-instans med auktoriteten "ADMIN" och sparar den i databasen.
			Role adminRole = roleRepository.save(new Role("ADMIN"));

			//Skapar en ny Role-instans med auktoriteten "USER" och sparar den i databasen.
			roleRepository.save(new Role("USER"));

			//Skapar en uppsättning av roller och lägger till adminRole i uppsättningen.
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			//Skapar en ny User-instans med användarnamnet "admin", ett krypterat lösenord och rollerna.
			//Användaren sparas i databasen genom UserRepository.
			
			User admin = new User(1L, "admin", passwordEncoder.encode("password"), roles);

			userRepository.save(admin);

			//skapar städer
			City stockholm = new City(1L, "Stockholm");
			City gothenburg = new City(2L, "Göteborg");
			City malmo = new City(3L, "Malmö");

			//sparar städer
			cityRepository.save(stockholm);
			cityRepository.save(gothenburg);
			cityRepository.save(malmo);


			//Skapar färdigt företag
			Company ica = new Company("Ica", stockholm);
			Company coop = new Company( "Coop", gothenburg);
			Company cityGross = new Company( "City Gross", malmo);
			//sparar företag
			companyRepository.save(ica);
			companyRepository.save(coop);
			companyRepository.save(cityGross);
		};
	}
}
