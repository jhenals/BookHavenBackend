package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Blog;
import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.services.BlogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlogController {

    @Autowired
    BlogServices blogServices;

    @GetMapping("blogs")
    public List<Blog> getAllBlogs(){
        return blogServices.getAllBlogs();
    }


}
