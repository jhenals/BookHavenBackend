package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Category;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;
    public void createCategory (Category category){
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

}
