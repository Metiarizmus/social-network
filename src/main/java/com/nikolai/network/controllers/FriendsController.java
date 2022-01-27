package com.nikolai.network.controllers;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.service.interfaces.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class FriendsController {

    @Autowired
    private FriendsService friendsService;

    @GetMapping("/friends")
    public String show(Principal principal) {
        return "friends";
    }

    @RequestMapping("/friends/SearchFriend")
    public String searchFriend(Model model) {
        model.addAttribute("nameFragment", "findFriend");
        return "friends";
    }

    @RequestMapping("/friends/SearchPeople")
    public String searchPeople(Model model) {
        model.addAttribute("nameFragment", "findPeople");
        return "friends";
    }

    @PostMapping("/friends/searchPeople")
    public ModelAndView  searchPeople(Model model, @Param("keyword") String keyword) {
        ModelAndView modelAndView = new ModelAndView();

        List<UserDto> list = friendsService.search(keyword);

        modelAndView.setViewName("friends");
        modelAndView.addObject("peoples", list);

        return modelAndView;
    }


}
