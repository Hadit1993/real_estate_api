package com.hadit1993.realestate.api.security;


import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CoffeeshopUserDetails implements UserDetails {

    private final String email;
    private final String password;

    private final UserRole userRole;

    private final boolean isActive;

    public CoffeeshopUserDetails(String email, String password, UserRole userRole, boolean isActive) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.isActive = isActive;
    }

    public static CoffeeshopUserDetails create(User user) {

        return new CoffeeshopUserDetails(user.getEmail(), user.getPassword(), user.getUserRole(), user.isActive());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
