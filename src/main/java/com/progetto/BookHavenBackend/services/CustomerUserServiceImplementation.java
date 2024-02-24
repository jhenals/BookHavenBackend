package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomerUserServiceImplementation implements UserDetailsService {

    private UserRepository userRepository;

    public CustomerUserServiceImplementation(UserRepository userRepository){
        this.userRepository= userRepository;

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user= (User) userRepository.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found with email: " +username);
        }

        List<GrantedAuthority> authorities= new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}

