package com.nikolai.network.controllers;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.service.interfaces.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class GeneralController {

    @Autowired
    private FriendsService friendsService;

    @ModelAttribute
    private void generalAttribute(Principal principal, Model model) {
        UserDto userDto = friendsService.getUserDto(principal.getName());
        model.addAttribute("user", userDto);
        model.addAttribute("requestUser", friendsService.listUsersByStatus(userDto.getId(), StatusFriends.WAITING));
    }

}
