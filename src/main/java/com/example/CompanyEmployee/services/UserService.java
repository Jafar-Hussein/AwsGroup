package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.City;
import com.example.CompanyEmployee.models.Company;
import com.example.CompanyEmployee.models.Role;
import com.example.CompanyEmployee.models.User;
import com.example.CompanyEmployee.repository.CityRepository;
import com.example.CompanyEmployee.repository.CompanyRepository;
import com.example.CompanyEmployee.repository.RoleRepository;
import com.example.CompanyEmployee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Detta är tjänsteklassen för User.
 * Den hanterar affärslogiken för User-entiteten.
 * @author Fredrik
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Denna metod används för att hämta alla användare.
     * @return En lista över alla användare.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Denna metod används för att hämta alla användare.
     * @return En lista över alla användare.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Denna metod används för att uppdatera en användare med dess ID.
     * @param id Användarens ID.
     * @param newUser Den uppdaterade användaren.
     * @return Den uppdaterade användaren.
     */
    public User updateUser(Long id, User newUser) {
        return userRepository.findById(id)
                .map(user -> {
                    if (newUser.getUsername() != null) {
                        user.setUsername(newUser.getUsername());
                    }
                    if (newUser.getPassword() != null) {
                        user.setPassword(encoder.encode(newUser.getPassword()));
                    }
                    if (newUser.getCompany() != null) {
                        Company company = companyRepository.findById(newUser.getCompany().getCompanyId())
                                .orElseThrow(() -> new IllegalArgumentException("Company with id " + newUser.getCompany().getCompanyId() + " does not exist"));
                        user.setCompany(company);
                    }
                    if (newUser.getCity() != null) {
                        City city = cityRepository.findById(newUser.getCity().getCityId())
                                .orElseThrow(() -> new IllegalArgumentException("City with id " + newUser.getCity().getCityId() + " does not exist"));
                        user.setCity(city);
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
    }

    /**
     * Denna metod används för att ta bort en användare med dess ID.
     * @param id Användarens ID.
     */
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            System.out.println("User with id: " + id + " removed");
        }
        else {
            throw new IllegalArgumentException("User with id " + id + " does not exist");
        }
    }

    /**
     * Denna metod används för att hämta den nuvarande inloggade användaren.
     * @return Den nuvarande inloggade användaren.
     */
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }

    /**
     * Denna metod används för att ändra rollen för en användare.
     * den ropar användaren från databasen med userId variabeln och sedan ropar den rollen från databasen med roleId variabeln.
     * Den skapar en ny uppsättning roller och lägger sedan rensar den nuvarande uppsättningen av roller för användaren och ger användaren den nya rollen.
     * @param userId Användarens ID.
     * @param roleId Den nya rollen för användaren.
     * @return Användaren med den uppdaterade rollen.
     */

    @Transactional
    public User updateUserRole(Long userId, Integer roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " does not exist"));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " does not exist"));

        // skapa en ny uppsättning roller och lägg till den nya rollen i uppsättningen
        Set<Role> updatedAuthorities = (Set<Role>) new HashSet<>(user.getAuthorities());

        // rensa den nuvarande uppsättningen av roller
        updatedAuthorities.clear();

        // spara den nya rollen i uppsättningen
        updatedAuthorities.add(role);

        // sätt den nya uppsättningen av roller som användarens roller
        user.setAuthorities(updatedAuthorities);

        // Spara användaren med den nya uppsättningen av roller och returnera användaren
        return userRepository.save(user);
    }





    /**
     * Denna metod används för att ladda en användare med dess användarnamn.
     * @param username Användarens användarnamn.
     * @return Användaren med det angivna användarnamnet.
     * @throws UsernameNotFoundException Om användaren inte hittas med det angivna användarnamnet.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}
