package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "email",  nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    @NotEmpty
    private String password;


    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "mobile")
    private String mobile;

    private LocalDateTime createdAt;

    public User(User user) {
        this.firstname= user.getFirstname();
        this.lastname= user.getLastname();
        this.email= user.getEmail();
        this.password= user.getPassword();
    }  //post mapping

    public User() {
    }

    public void setFirstName(String firstName) {
        this.firstname= firstName;
    }

    public void setLastName(String lastName) {
        this.lastname=lastName;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstName) {
        this.firstname= firstName;
    }

    public void setLastname(String lastName) {
        this.lastname=lastname;
    }

}