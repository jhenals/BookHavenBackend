package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Blog;
import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServices {
    @Autowired
    BlogRepository blogRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }
}
