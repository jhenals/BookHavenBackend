package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.isDefault = true ")
    Address findDefaultByUserId(@Param("userId") String userId);

    @Query("SELECT a FROM Address a WHERE a.user.id = :userId")
    List<Address> findAllByUserId(@Param("userId") String userId);
}