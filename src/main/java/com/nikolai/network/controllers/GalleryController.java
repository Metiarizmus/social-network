package com.nikolai.network.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GalleryController {


    @GetMapping("/gallery")
    public String showGallery(@RequestParam("email") String email) {
        return "gallery";
    }
}
