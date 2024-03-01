package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.configurations.KeycloakConfig;
import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.services.AdminService;
import com.progetto.BookHavenBackend.services.KeycloakService;
import com.progetto.BookHavenBackend.services.UserService;
import com.progetto.BookHavenBackend.support.ResponseMessage;
import com.progetto.BookHavenBackend.support.exceptions.MailUserAlreadyExistsException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.text.MessageFormat;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    KeycloakConfig keycloakUtil;

    @Autowired
    AdminService adminService;

    @Autowired
    KeycloakService keycloakService;

    @Value("${realm}")
    private String realm;

    @Value("${client-id}")
    private String clientId;

    @GetMapping("/keycloak/users")
    @PreAuthorize("hasRole('ROLE_admin')")
    public List<User> getAllUsers(){
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        List<UserRepresentation> userRepresentations=
                keycloak.realm(realm).users().list();
        return keycloakService.mapUsers(userRepresentations);
    }

    @GetMapping("/{userId}/wishlist")
    public Set<Book> getWishlist(@PathVariable("userId") String userId){
        return userService.getWishlist(userId);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) throws UserNotFoundException{
        try{
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch ( UserNotFoundException e ){
            return new ResponseEntity(new ResponseMessage("Error: User not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/registration")
    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) throws MailUserAlreadyExistsException {
        try{
            User added= userService.registerUser(user);
            return new ResponseEntity<>(added, HttpStatus.OK);
        }catch(MailUserAlreadyExistsException e){
            return new ResponseEntity(new ResponseMessage("Error: User already exists"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable String id , @RequestBody User user) throws UserNotFoundException {
        try{
            userService.updateUser(id, user);
        }catch(UserNotFoundException e){
           throw new UserNotFoundException();
        }

    }

    @RequestMapping(value="/deleteuser/{id}", method= RequestMethod.DELETE)
    public void deleteUser(@PathVariable String id) throws  UserNotFoundException{
        try{
            userService.deleteAccountById(id);
        }catch(UserNotFoundException e){
            throw new UserNotFoundException();
        }
    }


}
