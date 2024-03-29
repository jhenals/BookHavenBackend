package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstname (String firstname);
    List<User> findByLastname (String lastname);
    List<User> findByFirstnameAndLastname(String firstname, String lastname);
    List<User> findByEmail (String email);

    User findById(String id);

    boolean existsByEmail(String email);

    void deleteById(String id);


}