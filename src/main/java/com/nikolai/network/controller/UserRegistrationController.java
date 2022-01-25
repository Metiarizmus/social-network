package com.nikolai.network.controller;

import com.nikolai.network.dto.UserRegistrDto;
import com.nikolai.network.model.User;
import com.nikolai.network.service.interfaces.UserRegistrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
@PropertySource("classpath:validationMessages.properties")
public class UserRegistrationController {

    @Value("${user.email.exist}")
    private String emailExist;

    @Value("${user.password.match}")
    private String matchPassword;

    @Autowired
    private UserRegistrService userService;

    @ModelAttribute("user")
    public UserRegistrDto userRegistrationDto() {
        return new UserRegistrDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrDto userRegistrDto,
                                      BindingResult result) {

        User existing = userService.findByEmail(userRegistrDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null,emailExist);
        }

        if(!userRegistrDto.getPassword().equals(userRegistrDto.getConfirmPassword())){
            result.rejectValue("confirmPassword", null, matchPassword);
        }

        if (result.hasErrors()) {
            return "registration";
        }

        userService.saveUser(userRegistrDto);

        return "profile";
    }
}
