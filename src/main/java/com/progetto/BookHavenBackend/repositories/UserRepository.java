package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstname (String firstname);
    List<User> findByLastname (String lastname);
    List<User> findByFirstnameAndLastname(String firstname, String lastname);
    List<User> findByEmail (String email);

    User findById(long id);
    boolean existsByEmail(String email);
}