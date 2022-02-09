package com.nikolai.network.controllers;

import com.nikolai.network.dto.ContentGroupDto;
import com.nikolai.network.dto.GroupDto;
import com.nikolai.network.dto.UserDto;
import com.nikolai.network.enums.ActionWithGroup;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.ContentGroup;
import com.nikolai.network.model.Group;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.GroupRepository;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.impl.GroupContentServiceImpl;
import com.nikolai.network.service.impl.GroupServiceImpl;
import com.nikolai.network.service.interfaces.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/groups")
public class GroupsController extends GeneralController{

    @Autowired
    private FriendsService friendsService;

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupContentServiceImpl groupContentImpl;

    @ModelAttribute
    private void generalAttribute(Principal principal, Model model) {
        UserDto userDto = friendsService.getUserDto(principal.getName());

        model.addAttribute("mangmGroups", groupService.listMyControllingGroups(userDto.getId()));
    }

    @RequestMapping
    public ModelAndView getMyGroups(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("groups");
        modelAndView.addObject("nameFragment", "findMyGroups");

        User user = userRepository.findByEmail(principal.getName());

        modelAndView.addObject("myGroups", groupService.listMyGroups(user));

        return modelAndView;
    }

    @PostMapping("/createNew")
    public String addNew( Principal principal,
                         @RequestParam("image") MultipartFile file,
                         @RequestParam("name") String name
                         ) {
        User user = userRepository.findByEmail(principal.getName());

        Group group = null;
        try {
            group = new Group(name, file.getBytes(), user.getId());
            groupService.creatGroup(group, user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/groups";
    }

    @RequestMapping("/showProfileGroup")
    public String adminGroups(@RequestParam Integer id, ModelMap map, Principal principal) {

        map.addAttribute("nameFragment", "adminGroup");
        Group group = groupRepository.getById(id);

        List<ContentGroupDto> list = groupContentImpl.listContentForGroup(group);

        User user = userRepository.getById(group.getCreaterId());
        map.addAttribute("userCreater", user);

        map.addAttribute("group",  groupService.adminModeGroup(group));
        map.addAttribute("content", list);
        map.addAttribute("subscribers", groupService.listSubscribersUserForGroup(group));

        return "groups";
    }

    @PostMapping("/addContentGroup")
    public RedirectView addContentGroup(@RequestParam Integer idGroup,
                                        @RequestParam("textContent") String text,
                                        @RequestParam("fileContent") MultipartFile file,
                                        RedirectAttributes redirectAttributes
                                  ){

       Group group = groupRepository.getById(idGroup);
       ContentGroup contentGroup = new ContentGroup();

       if (text != null) {
           contentGroup.setContent(text);
       }
       if (file != null) {
           try {
               contentGroup.setFileContent(file.getBytes());
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       contentGroup.setGroup(group);

       groupContentImpl.saveGroupContent(contentGroup);

        redirectAttributes.addAttribute("id", idGroup);
        return new RedirectView("showProfileGroup");

    }

    @RequestMapping("/findNewGroup")
    public String searchNewGroup(ModelMap map){
        map.addAttribute("nameFragment", "findGroup");

        return "groups";
    }

    @PostMapping("/searchGroup")
    public ModelAndView  searchPeople(Model model, @Param("keyword") String keyword) {
        ModelAndView modelAndView = new ModelAndView("groups");

        List<GroupDto> list = groupService.findNewGroup(keyword);

        model.addAttribute("nameFragment", "findGroup");
        modelAndView.addObject("groups", list);

        return modelAndView;
    }

    @RequestMapping("/showGroup")
    public RedirectView showGroupById(@RequestParam Integer id, RedirectAttributes redirectAttributes, Model model){

        redirectAttributes.addAttribute("id", id); //id group


        return new RedirectView("showProfileGroup");
    }


    @RequestMapping("/actionGroup")
    public RedirectView groupsAction(@RequestParam Integer idGroup,@RequestParam String action,
                                     RedirectAttributes redirectAttributes, Principal principal) {

        User user = userRepository.findByEmail(principal.getName());
        Group group =  groupRepository.getById(idGroup);
        redirectAttributes.addAttribute("id", idGroup);


        if (action.equals("SUBSCRIBE")){
            groupService.actionWithGroup(user,group, ActionWithGroup.SUBSCRIBE);

            return new RedirectView("showProfileGroup");
        }
        else if (action.equals("UNSUBSCRIBE")){
            groupService.actionWithGroup(user, group, ActionWithGroup.UNSUBSCRIBE);

            return new RedirectView("showProfileGroup");

        } else {
            groupService.actionWithGroup(user, group, ActionWithGroup.DELETE);

            return new RedirectView("groups");

        }


    }




}

