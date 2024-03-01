package com.example.CompanyEmployee.controller;

import com.example.CompanyEmployee.models.User;
import com.example.CompanyEmployee.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Detta är kontrollerklassen för User.
 * Den hanterar HTTP-förfrågningar och svar för User-entiteten.
 * @author Fredrik
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Denna metod används för att hämta alla användare.
     * @return En lista över alla användare.
     */
    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Denna metod används för att hämta en användare med dess ID.
     * @param id Användarens ID.
     * @return Användaren med det angivna ID:et.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Denna metod används för att uppdatera en användare med dess ID.
     * @param id Användarens ID.
     * @param user Den uppdaterade användaren.
     * @return Den uppdaterade användaren.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}/role/{roleId}")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id, @RequestParam Integer roleId) {
        User updatedUser = userService.updateUserRole(id, roleId);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Denna metod används för att ta bort en användare med dess ID.
     * @param id Användarens ID.
     * @return Ett strängmeddelande som indikerar att användaren har tagits bort.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
