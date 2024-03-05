package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
}