package com.hadit1993.realestate.api.features.users.entities;


import com.hadit1993.realestate.api.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class User extends Auditable {

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('CUSTOMER','SELLER','ADMIN') default 'CUSTOMER'")
    private UserRole userRole = UserRole.CUSTOMER;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean isActive = false;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
