package com.nikolai.network.controllers;

import com.nikolai.network.model.ChatMessage;
import com.nikolai.network.model.ChatNotification;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.impl.ChatMessageServiceImpl;
import com.nikolai.network.service.impl.ChatRoomServiceImpl;
import com.nikolai.network.service.interfaces.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ChatController extends GeneralController {
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageServiceImpl chatMessageService;
    @Autowired
    private ChatRoomServiceImpl chatRoomService;


    @RequestMapping("/mess")
    public String chat(Principal principal, ModelMap map) {
        User user = userRepository.findByEmail(principal.getName());

        map.addAttribute("friends", friendsService.listMyFriend(user.getId()));

        return "chat";
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatId(chatMessage.getSender().getId(), chatMessage.getRecipient().getId(), true);
        chatMessage.setChatId(chatId.get());

        ChatMessage saved = chatMessageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipient().getId()), "/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSender().getId(),
                        saved.getSenderName()));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable Integer senderId,
            @PathVariable Integer recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages(@PathVariable Integer senderId,
                                              @PathVariable Integer recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage(@PathVariable Integer id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }

}
