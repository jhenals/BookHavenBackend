package com.progetto.BookHavenBackend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/check-endpoint")
    public String checkEndpoint(){
        return "Admin endpoint is working and is only accessible by admininstrator";
    }


}
