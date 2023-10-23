package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.services.UserService;
import com.progetto.BookHavenBackend.support.ResponseMessage;
import com.progetto.BookHavenBackend.support.exceptions.MailUserAlreadyExistsException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) throws UserNotFoundException{
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
    public void updateUser(@PathVariable long id , @RequestBody User user) throws UserNotFoundException {
        try{
            userService.updateUser(id, user);
        }catch(UserNotFoundException e){
           throw new UserNotFoundException();
        }

    }

    @RequestMapping(value="/deleteuser/{id}", method= RequestMethod.DELETE)
    public void deleteUser(@PathVariable long id) throws  UserNotFoundException{
        try{
            userService.deleteAccountById(id);
        }catch(UserNotFoundException e){
            throw new UserNotFoundException();
        }
    }
}
