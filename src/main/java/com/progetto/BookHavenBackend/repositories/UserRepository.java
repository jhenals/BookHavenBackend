package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstName (String firstname);
    List<User> findByLastName (String lastname);
    List<User> findByFirstNameAndLastName(String firstname, String lastname);
    List<User> findByEmail (String email);
    boolean existsByEmail(String email);
}