package com.example.CompanyEmployee.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * Role model class
 * Den här klassen är en modell för rollen som används i säkerhetskonfigurationen.
 * Den har en roll_id och en behörighet.
 * Den implementerar GrantedAuthority som är en del av Spring Security.
 * @Athor Clara Brorson
 */
@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="role_id")
    private Integer roleId;

    private String authority;

    public Role(){
        super();
    }

    public Role(String authority){
        this.authority = authority;
    }

    public Role(Integer roleId, String authority){
        this.roleId = roleId;
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return this.authority;
    }

    public void setAuthority(String authority){
        this.authority = authority;
    }

    public Integer getRoleId(){
        return this.roleId;
    }

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }
}

