package com.nikolai.network.controllers;

import com.nikolai.network.dto.UserRegistrDto;
import com.nikolai.network.model.User;
import com.nikolai.network.service.interfaces.UserRegistrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //convert the date Note that the conversion here should always be in the same format as the string passed in, e.g. 2015-9-9 should be yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor is a custom date editor
    }

    @PostMapping
    public RedirectView registerUserAccount(@ModelAttribute("user") @Valid UserRegistrDto userRegistrDto,
                                            BindingResult result,
                                            RedirectAttributes redirectAttributes
    ) {

        User existing = userService.findByEmail(userRegistrDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, emailExist);
        }

        if (!userRegistrDto.getPassword().equals(userRegistrDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", null, matchPassword);
        }

        redirectAttributes.addAttribute("email", userRegistrDto.getEmail());


        if (result.hasErrors()) {
            //return new RedirectView("");
        }

        userService.saveUser(userRegistrDto);

        return new RedirectView("profile");
    }
}
