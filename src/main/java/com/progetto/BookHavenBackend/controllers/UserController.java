package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.services.UserService;
import com.progetto.BookHavenBackend.support.ResponseMessage;
import com.progetto.BookHavenBackend.support.exceptions.MailUserAlreadyExistsException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = "*",
        methods = { RequestMethod.GET }
)
@RequestMapping("/api/v1")
public class UserController {

    @GetMapping("/hello")
    public Message hello(){
        var jwt = SecurityContextHolder.getContext().getAuthentication();
        var message= MessageFormat.format(
                "Hello {0}! How is it going today? ",
                jwt.getName()
        ) ;

        return new Message(message);

    }

    record Message( String message) {};
    @Autowired
    private UserService userService;

    @GetMapping(path = "/users")
    public String getUserInfo(Model model) {

        final DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String userId = "";

        OidcIdToken token = user.getIdToken();

        Map<String, Object> customClaims = token.getClaims();

        if (customClaims.containsKey("user_id")) {
            userId = String.valueOf(customClaims.get("user_id"));
        }

        model.addAttribute("username", user.getName());
        model.addAttribute("userID", userId);
        return "userInfo";
    }






    @PreAuthorize("hasRole('client_admin')")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('client_admin')")
    public String helloAdmin(){
        return "Hello, admin";
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
