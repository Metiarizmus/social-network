package com.nikolai.network.service.impl;

import com.nikolai.network.enums.StatusMessage;
import com.nikolai.network.model.ChatMessage;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.ChatMessageRepository;
import com.nikolai.network.repository.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageServiceImpl {

    @Autowired
    private ChatMessageRepository repository;
    @Autowired
    private ChatRoomServiceImpl chatRoomService;
    @Autowired
    private UserRepository userRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(StatusMessage.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }


    public long countNewMessages(Integer senderId, Integer recipientId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> recipient = userRepository.findById(recipientId);

        return repository.countBySenderAndRecipientAndStatus(sender.get(), recipient.get(), StatusMessage.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(Integer senderId, Integer recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages =
                chatId.map(cId -> repository.findByChatId(Integer.valueOf(cId))).orElse(new ArrayList<>());

//        if(messages.size() > 0) {
//            updateStatuses(senderId, recipientId, StatusMessage.DELIVERED);
//        }

        return messages;
    }

    public ChatMessage findById(Integer id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(StatusMessage.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new NotFoundException("can't find message (" + id + ")"));
    }

//    public void updateStatuses(String senderId, String recipientId, StatusMessage status) {
//
//        Query query = new Query(
//                Criteria.where("senderId").is(senderId)
//                        .and("recipientId").is(recipientId));
//        Update update = Update.update("status", status);
//        mongoOperations.updateMulti(query, update, ChatMessage.class);
//    }
}
