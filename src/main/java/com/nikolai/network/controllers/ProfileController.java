package com.nikolai.network.controllers;

import com.nikolai.network.dto.ImageDto;
import com.nikolai.network.dto.UserDto;
import com.nikolai.network.dto.UserRequestDto;
import com.nikolai.network.service.interfaces.FriendsService;
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
import java.security.Principal;
import java.util.*;

@Controller
public class ProfileController extends GeneralController{

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private FriendsService friendsService;
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
    public String root(Model map, Principal principal) {

        List<ImageDto> images = galleryService.getAllImageForUser(principal.getName());

        logger.info("show profile");

        UserDto profileUserDto = profileService.getUserDto(principal.getName());

        List<UserRequestDto> list = friendsService.listMyFriend(profileUserDto.getId());
        map.addAttribute("friends", list);

        map.addAttribute("images", images);
        map.addAttribute("user",profileUserDto);

        return "profile";
    }


}


