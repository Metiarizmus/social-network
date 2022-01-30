package com.nikolai.network.repository;

import com.nikolai.network.model.Friend;
import com.nikolai.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendRepository extends JpaRepository<Friend, Integer> {
    Friend findByFirstUserAndSecondUser(User from, User to);

}
