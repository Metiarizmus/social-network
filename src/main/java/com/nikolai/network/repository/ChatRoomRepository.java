package com.nikolai.network.repository;

import com.nikolai.network.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    Optional<ChatRoom> findByRecipientIdAndSenderId(Integer senderId, Integer recipientid);

}
