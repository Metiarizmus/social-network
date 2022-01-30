package com.nikolai.network.controllers;

import com.nikolai.network.dto.ImageDto;
import com.nikolai.network.dto.UserDto;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.GalleryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Random;

@Controller
public class GalleryController{

    private static final Logger logger = LoggerFactory.getLogger(GalleryController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GalleryService galleryService;

    @GetMapping("/gallery")
    public String showGallery(Principal principal,
                              Model map) {
        List<ImageDto> images = galleryService.getAllImageForUser(principal.getName());

        UserDto userDto = galleryService.getUserDto(principal.getName());

        map.addAttribute("images", images);
        map.addAttribute("user",userDto);
        return "gallery";
    }

    @PostMapping("/savePhoto")
    public RedirectView  saveAvatar(@RequestParam(name = "image", required = true) MultipartFile file,
                             @RequestParam("email") String email,
                             RedirectAttributes redirectAttributes
                             ) {

        User user = userRepository.findByEmail(email);
        redirectAttributes.addAttribute("email", email);
        try {
            galleryService.saveImage(file, user);
        } catch (IOException e) {
            logger.info("error in save image");

        }

         return new RedirectView("gallery");
    }
}
