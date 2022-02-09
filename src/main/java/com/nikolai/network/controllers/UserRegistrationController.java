package com.nikolai.network.controllers;

import com.nikolai.network.model.User;
import com.nikolai.network.service.interfaces.UserRegistrService;
import com.nikolai.network.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/registration")
@PropertySource("classpath:validationMessages.properties")
public class UserRegistrationController {

    @Autowired
    private UserRegistrService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping
    public String showRegistrationForm(ModelMap map) {
        map.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") User user,
                                      BindingResult result
                                      ) {

        userValidator.validate(user,result);

        if (result.hasErrors()) {
            return "registration";
        }

        userService.saveUser(user);

        return "login";
    }
}
