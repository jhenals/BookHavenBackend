package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "blog_title", nullable = false)
    private String blogTitle;

    @Column(name = "blog_author")
    private String blogAuthor;

    @Column(name = "blog_link", nullable = false)
    private String blogLink;

    @Column(name = "author_link")
    private String authorLink;

    @Column(name = "blog_image")
    private String blogImage;

    @Column(name = "blog_genre")
    private String blogGenre;

}