package com.nikolai.network.controllers;

import com.nikolai.network.dto.ImageDto;
import com.nikolai.network.dto.UserDto;
import com.nikolai.network.dto.UserRequestDto;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.FriendsService;
import com.nikolai.network.service.interfaces.GalleryService;
import com.nikolai.network.service.interfaces.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/friends")
public class FriendsController {

    private static final Logger logger = LoggerFactory.getLogger(FriendsController.class);


    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    private void generalAttribute(Principal principal, Model model) {
        UserDto userDto = friendsService.getUserDto(principal.getName());
        model.addAttribute("user", userDto);
        model.addAttribute("requestUser", friendsService.listUsersByStatus(userDto.getId(), StatusFriends.WAITING));
    }

    @GetMapping
    public String show() {
        return "friends";
    }

    @RequestMapping("/searchFriend")
    public ModelAndView searchFriend(Model model, Principal principal) {

        ModelAndView modelAndView = new ModelAndView("friends");

        User user = userRepository.findByEmail(principal.getName());

        List<UserRequestDto> list = friendsService.listMyFriend(user.getId());

        model.addAttribute("nameFragment", "findFriend");
        modelAndView.addObject("peoples", list);

        return modelAndView;
    }


    @RequestMapping("/searchPeople")
    public String searchPeople(Model model) {

        model.addAttribute("nameFragment", "findPeople");

        return "friends";
    }


    @PostMapping("/searchPeople")
    public ModelAndView  searchPeople(Model model, @Param("keyword") String keyword) {
        ModelAndView modelAndView = new ModelAndView("friends");

        List<UserDto> list = friendsService.search(keyword);

        model.addAttribute("nameFragment", "findPeople");
        modelAndView.addObject("peoples", list);

        return modelAndView;
    }


    @RequestMapping("/profileUser")
    public ModelAndView showProfile(@RequestParam Integer id, Principal principal){
        ModelAndView modelAndView = new ModelAndView("profile");

        UserDto userDto = profileService.findById(id);

        User user = userRepository.findByEmail(principal.getName());

        List<UserRequestDto> myFriends = friendsService.listMyFriend(user.getId());

        for (UserRequestDto q : myFriends) {
            if (q.getId() == id){
                userDto.setYourFriend(true);
            }
        }

        List<UserRequestDto> friendsForProfile = friendsService.listMyFriend(id);


        logger.info("show profile for user with id = " + userDto.getId());

        List<ImageDto> images = galleryService.getAllImageForUser(userDto.getEmail());

        modelAndView.addObject("user", userDto);
        modelAndView.addObject("images", images);
        modelAndView.addObject("friends", friendsForProfile);

        return modelAndView;
    }

    @RequestMapping("/requestAddFriends")
    @ResponseBody
    public String requestAddFriends(@RequestParam Integer toId, Principal principal
                                   ) {

        System.out.println("toId = " + toId);
        User userFrom = userRepository.findByEmail(principal.getName());
        friendsService.requestToFriend(userFrom.getId(), toId);

        return "Request was send!";
    }

    @RequestMapping("/incomingFriend")
    public String incomingFriend(Model model) {

        model.addAttribute("nameFragment", "incomingFriend");

        return "friends";
    }

    @RequestMapping("/acceptedOrIgnoredFriend")
    public ModelAndView acceptedFriend(@RequestParam(name = "id") Integer idFrom, @RequestParam String state,
                                       Principal principal,
                                       Model model){

        ModelAndView modelAndView = new ModelAndView("friends");

        User userFrom = userRepository.findByEmail(principal.getName());
        Integer idTo = userFrom.getId();

        if ("accepted".equals(state)){
            friendsService.acceptedOrIgnoredFriend(idFrom, idTo, StatusFriends.ACCEPTED);
            logger.info("accepted friend with id = " + idFrom);

        }
        if ("ignored".equals(state)){
            friendsService.acceptedOrIgnoredFriend(idFrom, idTo, StatusFriends.IGNORED);
            logger.info("ignored friend with id = " + idFrom);
        }

        model.addAttribute("nameFragment", "incomingFriend");

        return modelAndView;
    }



}
