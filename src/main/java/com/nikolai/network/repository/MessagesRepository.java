package com.nikolai.network.repository;

import com.nikolai.network.model.Messages;
import com.nikolai.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    @Query("select m from Messages m where (m.sender= ?1 and m.recipient= ?2) or (m.sender= ?2 and m.recipient= ?1) ")
    List<Messages> listMessages(User from, User to);

}
