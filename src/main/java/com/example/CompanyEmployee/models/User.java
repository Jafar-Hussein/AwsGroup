package com.example.CompanyEmployee.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Detta är en modellklassen för User.
 * Den representerar User entiteten i databasen.
 * @author Fredrik
 */
@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_roles_junction",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> authorities;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


    /**
     * Detta är konstruktorn för User.
     * @param id Användarens ID.
     * @param username Användarens användarnamn.
     * @param password Användarens lösenord.
     * @param authorities Användarens roller.
     */
    public User(Long id, String username, String password, Set<Role> authorities) {  //constructor
        this.userId = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Standardkonstruktor för User.
     */
    public User() {
        super();
        this.authorities = new HashSet<Role>();
    }

    /**
     * Denna metod används för att hämta användarens roller.
     * @return Användarens roller.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return this.authorities;}

    /**
     * Denna metod används för att hämta användarens lösenord.
     * @return Användarens lösenord.
     */
    @Override
    public String getPassword() {return this.password;}

    /**
     * Denna metod används för att hämta användarens användarnamn.
     * @return Användarens användarnamn.
     */
    @Override
    public String getUsername() {return this.username;}

    /**
     * Denna metod används för att kontrollera om användarens konto inte har löpt ut.
     * @return True om kontot inte har löpt ut, annars false.
     */
    @Override
    public boolean isAccountNonExpired() {return true;}

    /**
     * Denna metod används för att kontrollera om användarens konto inte är låst.
     * @return True om kontot inte är låst, annars false.
     */
    @Override
    public boolean isAccountNonLocked() {return true;}

    /**
     * Denna metod används för att kontrollera om användarens referenser inte har löpt ut.
     * @return True om referenserna inte har löpt ut, annars false.
     */
    @Override
    public boolean isCredentialsNonExpired() {return true;}

    /**
     * Denna metod används för att kontrollera om användaren är aktiverad.
     * @return True om användaren är aktiverad, annars false.
     */
    @Override
    public boolean isEnabled() {return true;}


}
