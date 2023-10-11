package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}