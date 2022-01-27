package com.nikolai.network.controllers;

import com.nikolai.network.service.interfaces.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController{

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserProfileService profileService;


    @GetMapping("/")
    public String root(Model map, Principal principal) {

        map.addAttribute("user",profileService.getUserDto(principal.getName()));
        return "profile";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

}
