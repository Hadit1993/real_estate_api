package com.hadit1993.realestate.api.features.users.repositories;


import com.hadit1993.realestate.api.features.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);

    void deleteByEmail(String email);
}
