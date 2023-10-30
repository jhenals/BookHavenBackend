package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}