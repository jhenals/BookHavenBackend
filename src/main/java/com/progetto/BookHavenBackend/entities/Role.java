package com.progetto.BookHavenBackend.entities;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String roleName;
    Role(String roleName){
        this.roleName= roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
