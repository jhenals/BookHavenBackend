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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email",  nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;

    @Column(name = "password", nullable = false, unique = true)
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Role role;

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
        this.role= user.getRole();
    }  //post mapping

    public User() {

    }


    //@OneToMany(mappedBy="user", cascade=CascadeType.All)
    //private List<Address> address= new ArrayList<>();

    //@Embedded
    //@ElementCollection
    //@CollectionTable(name="payment_information",joinColumns=@JoinColumn(name="user_id"))
    //private List<PaymentInformation> paymentInformation= new ArrayList<>();
}