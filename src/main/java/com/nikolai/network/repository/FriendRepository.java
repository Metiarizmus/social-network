package com.nikolai.network.repository;

import com.nikolai.network.model.Friend;
import com.nikolai.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FriendRepository extends JpaRepository<Friend, Integer> {

    Friend findByUserFromAndUserTo(User from, User to);

    @Query("select f from Friend f  where (f.userFrom.id = ?1 or f.userTo.id = ?1) and f.statusFriends='ACCEPTED' group by(f.userFrom)")
    List<Friend> listMyFriends(Integer id);

}
