package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}