package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.configurations.KeycloakConfig;
import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.services.AdminService;
import com.progetto.BookHavenBackend.services.KeycloakService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

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

    @GetMapping("/check-endpoint")
    public String checkEndpoint(){
        return "Admin endpoint is working and is only accessible by admininstrator";
    }

    @GetMapping
    @RequestMapping("/keycloak/users")
    public List<User> getAllUsers(){
        Keycloak keycloak = keycloakUtil.getKeycloakInstance();
        List<UserRepresentation> userRepresentations=
                keycloak.realm(realm).users().list();
        return keycloakService.mapUsers(userRepresentations);
    }


}
