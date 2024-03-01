package com.example.CompanyEmployee.services;

import com.example.CompanyEmployee.models.Role;
import com.example.CompanyEmployee.models.User;
import com.example.CompanyEmployee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                        user.setPassword(newUser.getPassword());
                    }
                    if (newUser.getCompany() != null) {
                        user.setCompany(newUser.getCompany());
                    }
                    if (newUser.getCity() != null) {
                        user.setCity(newUser.getCity());
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
     * @param id Användarens ID.
     * @param role Den nya rollen för användaren.
     * @return Användaren med den uppdaterade rollen.
     */
    public User changeRole(Long id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));

        // skapa en ny roll och uppdatera användarens behörigheter med den nya rollen
        Role newRole = new Role(role);
        user.setAuthorities(Collections.singleton(newRole));
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
