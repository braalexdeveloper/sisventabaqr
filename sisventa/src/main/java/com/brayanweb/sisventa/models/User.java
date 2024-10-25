package com.brayanweb.sisventa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="users")
public class User implements UserDetails{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false,unique=true)
    private String email;
    
    @Column(nullable=false)
    private String password;
    
    @ManyToOne
    @JoinColumn(name="role_id",nullable=false)
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role.getName().toUpperCase()));
    }

    @Override
    public String getUsername() {
return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
return true; // Puedes implementar lógica real si lo necesitas
    }

    @Override
    public boolean isAccountNonLocked() {
return true; // Puedes implementar lógica real si lo necesitas
    }

    @Override
    public boolean isCredentialsNonExpired() {
return true; // Puedes implementar lógica real si lo necesitas
    }

    @Override
    public boolean isEnabled() {
return true; // Puedes implementar lógica real si lo necesitas
    }
    
    
}
