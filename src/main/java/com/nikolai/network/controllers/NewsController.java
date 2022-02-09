package com.nikolai.network.controllers;

import com.nikolai.network.dto.ContentGroupDto;
import com.nikolai.network.dto.UserDto;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.User;
import com.nikolai.network.service.impl.GroupContentServiceImpl;
import com.nikolai.network.service.impl.GroupServiceImpl;
import com.nikolai.network.service.impl.NewsServiceImpl;
import com.nikolai.network.service.interfaces.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController extends GeneralController{

    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private FriendsService friendsService;


    @RequestMapping
    public String showNews(Principal principal, ModelMap map) {

        List<ContentGroupDto> list = newsService.showContentGroupForUser(principal.getName());


        list.sort(new Comparator<ContentGroupDto>() {
            @Override
            public int compare(ContentGroupDto o1, ContentGroupDto o2) {
                return o2.getTimeCompare().compareTo(o1.getTimeCompare());
            }
        });
        map.addAttribute("content", list);

        return "news";
    }
}
