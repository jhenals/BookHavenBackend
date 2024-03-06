package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Address;
import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.PaymentInformation;
import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.repositories.AddressRepository;
import com.progetto.BookHavenBackend.repositories.PaymentInformationRepository;
import com.progetto.BookHavenBackend.repositories.UserRepository;
import com.progetto.BookHavenBackend.support.exceptions.MailUserAlreadyExistsException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PaymentInformationRepository paymentInformationRepository;


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
    public User getUserById( String id ) throws UserNotFoundException{
        Optional<User> userOptional= Optional.ofNullable(userRepository.findById(id));
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
            throw new UserNotFoundException();
        }
    }




    @Transactional(readOnly = false)
    public void updateUser(String id, User user) throws UserNotFoundException {
        Optional<User> userOptional= Optional.ofNullable(userRepository.findById(id));
        if( userOptional.isPresent() ){
            User newUser= userOptional.get();
            newUser=user;
            userRepository.save(newUser);
        }else {
            throw new UserNotFoundException();
        }
    }

    public void deleteAccountById(String id) throws UserNotFoundException {
        Optional<User> userOptional= Optional.ofNullable(userRepository.findById(id));
        if( userOptional.isPresent() ){
            userRepository.deleteById(id);
        }else {
            throw new UserNotFoundException();
        }
    }

    public List<User> mapUsers(List<UserRepresentation> userRepresentations) {
        List<User> users = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(userRepresentations)){
            userRepresentations.forEach(userRep -> {
                users.add(mapUser(userRep));
            });
        }
        return users;
    }

    private User mapUser(UserRepresentation userRep) {
        User user= new User();
        user.setId(userRep.getId());
        user.setFirstname(userRep.getFirstName());
        user.setLastname(userRep.getLastName());
        user.setEmail(userRep.getEmail());
        return user;
    }


    public UserRepresentation mapUserRep(User user) {
        UserRepresentation userRep= new UserRepresentation();
        userRep.setEmail(user.getEmail());
        userRep.setFirstName(user.getFirstname());
        userRep.setLastName(user.getLastname());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);
        List<CredentialRepresentation> creds= new ArrayList<>();
        CredentialRepresentation cred= new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        userRep.setCredentials(creds);
        return userRep;
    }

    public Set<Book> getWishlist(String userId) {
        Set<Book> wishlist = new LinkedHashSet<>();
        Optional<User> customerOptional = Optional.ofNullable(userRepository.findById(userId));
        if(customerOptional.isPresent()){
            User customer = customerOptional.get();
            wishlist = customer.getWishlist();
        }
        return wishlist;
    }

    public Address getDefaultAddress(String userId) {
        return addressRepository.findDefaultByUserId(userId);
    }

    public List<Address> getAllAddresses(String userId) {
        return addressRepository.findAllByUserId(userId);
    }

    public List<PaymentInformation> getAllPaymentMethod(String userId){
        return paymentInformationRepository.findAllByUserId(userId);
    }
}
