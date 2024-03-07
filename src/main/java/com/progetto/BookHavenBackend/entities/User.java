package com.progetto.BookHavenBackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "email",  nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;

    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "mobile")
    private String mobile;

    @Column(name="address")
    private String address;

    @Column(name ="createdAt")
    private LocalDateTime createdAt;

    @ElementCollection
    @Column(name = "role")
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "owner_id"))
    private Set<String> roles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Book> wishlist = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Order> archivedOrder = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Address> addresses = new LinkedHashSet<>();

/*
    public User(User user) {
        this.firstname= user.getFirstname();
        this.lastname= user.getLastname();
        this.email= user.getEmail();
        this.password= user.getPassword();
        this.roles= user.getRoles();
        this.address= user.getAddress();
    }  //post mapping
*/

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