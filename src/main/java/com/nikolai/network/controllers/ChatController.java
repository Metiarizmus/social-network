package com.nikolai.network.controllers;

import com.nikolai.network.dto.MessageDto;
import com.nikolai.network.dto.MessageResponseDto;
import com.nikolai.network.model.Messages;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.impl.MessageServiceImpl;
import com.nikolai.network.service.interfaces.FriendsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class ChatController extends GeneralController {
    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageServiceImpl messageService;

    @Autowired private SimpMessagingTemplate messagingTemplate;


    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);


    @RequestMapping("/mess")
    public String chat(Principal principal, ModelMap map) {
        User user = userRepository.findByEmail(principal.getName());

        map.addAttribute("friends", friendsService.listMyFriend(user.getId()));

        return "chat";
    }


    @MessageMapping("/chat/{to}")
    public void sendMessagePersonal(@DestinationVariable("to") Integer to, MessageDto message) {

        logger.info("send message from " + message.getFromLogin() + " to id = " + to);

        var chatId = message.getIdTopic();

        messageService.sendMessage(to,message);

        messagingTemplate.convertAndSend("/topic/messages/" + chatId,message);
    }


    @GetMapping("/mess/listmessage/{from}/{to}")
    public @ResponseBody List<MessageResponseDto> getListMessageChat(@PathVariable("from") Integer from, @PathVariable("to") Integer to){

        List<MessageResponseDto> list = messageService.getListMessage(from, to);

        logger.info("all messages between users");

        return list;

    }

}
