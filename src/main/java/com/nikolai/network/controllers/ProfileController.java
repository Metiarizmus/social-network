package com.nikolai.network.controllers;

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

@Controller
public class ProfileController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserProfileService profileService;

    @PostMapping("/saveAvatar")
    public String saveAvatar(@RequestParam("image") MultipartFile file, Model model) {

        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        try {
            profileService.saveAvatar(file.getBytes(), getCurrentlyEmail());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }


}


