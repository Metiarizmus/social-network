package com.nikolai.network.controllers;

import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/chat")
public class ChatController extends GeneralController{

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping
    public String chat(Principal principal, ModelMap map) {
        User user = userRepository.findByEmail(principal.getName());

        map.addAttribute("friends", friendsService.listMyFriend(user.getId()));

        return "chat";
    }
}
