package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
    @Query("SELECT p FROM PaymentInformation  p WHERE p.user.id = :userId ")
    List<PaymentInformation> findAllByUserId(@Param("userId") String userId);
}