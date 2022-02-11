package com.nikolai.network.service.impl;

import com.nikolai.network.dto.MessageDto;
import com.nikolai.network.dto.MessageResponseDto;
import com.nikolai.network.model.Messages;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.MessagesRepository;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl extends BaseServiceImpl{

    @Autowired private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired private MessagesRepository messagesRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private DtoConvert dtoConvert;

    public void sendMessage(Integer to, MessageDto message) {
        Optional<User> recipient = (userRepository.findById(to));
        Optional<User> sender = (userRepository.findById(message.getFromLogin()));

        Messages messages = new Messages(sender.get(), recipient.get(), message.getMessage());
        messages.setDate(dateNow());

        messagesRepository.save(messages);

    }

    public List<MessageResponseDto> getListMessage(@PathVariable("from") Integer from, @PathVariable("to") Integer to){

        Optional<User> recipient = (userRepository.findById(to));
        Optional<User> sender = (userRepository.findById(from));

        List<Messages> list = messagesRepository.listMessages(sender.get(),recipient.get());

        List<MessageResponseDto> messageDtoList = new ArrayList<>();

        for (Messages q : list) {
            messageDtoList.add(dtoConvert.convertToMessageDto(q));
        }

        return messageDtoList;
    }
}
