package com.nikolai.network.repository;

import com.nikolai.network.enums.StatusMessage;
import com.nikolai.network.model.ChatMessage;
import com.nikolai.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    Integer countBySenderAndRecipientAndStatus(User sender, User recipient, StatusMessage statusMessage);

    List<ChatMessage> findByChatId(Integer chatId);
}
