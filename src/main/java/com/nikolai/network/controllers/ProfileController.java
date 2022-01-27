package com.nikolai.network.controllers;

import com.nikolai.network.dto.ImageDto;
import com.nikolai.network.service.interfaces.GalleryService;
import com.nikolai.network.service.interfaces.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class ProfileController{

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private GalleryService galleryService;


    @PostMapping("/saveAvatar")
    public String saveAvatar(@RequestParam("image") MultipartFile file,
                             @RequestParam("email") String email,
                             Model model) {

        // check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        try {
            profileService.saveAvatar(file.getBytes(), email);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    @GetMapping("/profile")
    public String root(@RequestParam("email") String email, Model map) {
        logger.info("user object for " + email);
        List<ImageDto> images = galleryService.getAllImageForUser(email);

        map.addAttribute("images", images);
        map.addAttribute("countImage", images.size());
        map.addAttribute("user",profileService.getUserDto(email));
        return "profile";
    }


}


