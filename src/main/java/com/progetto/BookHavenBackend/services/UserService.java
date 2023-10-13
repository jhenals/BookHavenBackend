package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.repositories.UserRepository;
import com.progetto.BookHavenBackend.support.exceptions.MailUserAlreadyExistsException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly=false, propagation= Propagation.REQUIRED)
    public User registerUser( User user ) throws MailUserAlreadyExistsException {
        if ( userRepository.existsByEmail(user.getEmail()) ){
            throw new MailUserAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById( long id ) throws UserNotFoundException{
        Optional<User> userOptional= Optional.ofNullable(userRepository.findById(id));
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
            throw new UserNotFoundException();
        }
    }

    @Transactional(readOnly = false)
    public void updateUser(long id, User user) throws UserNotFoundException {
        Optional<User> userOptional= Optional.ofNullable(userRepository.findById(id));
        if( userOptional.isPresent() ){
            User newUser= userOptional.get();
            newUser=user;
            userRepository.save(newUser);
        }else {
            throw new UserNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public void deleteUser(long id){
        userRepository.deleteById(id);
    }

}
