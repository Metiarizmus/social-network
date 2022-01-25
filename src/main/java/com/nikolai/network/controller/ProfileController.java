package com.nikolai.network.controller;

import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ProfileController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileService profileService;

    @PostMapping("/saveAvatar")
    public String saveAvatar(@RequestParam("file") MultipartFile file, Model model) {

        System.err.println("im here");

        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "redirect:/profile";
        }

        try {
            profileService.saveAvatar(file.getBytes(), getCurrentlyEmail());

        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/profile";
    }


}


