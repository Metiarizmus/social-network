package com.nikolai.network.repository;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("SELECT u from User u where concat(u.firstName,u.lastName) like %?1%")
    List<User> search(String keyword);

    @Query("select u from User u join Friend f on u.id=f.firstUser.id where f.secondUser.id = ?1 and f.statusFriends = ?2")
    List<User> listUsersByStatusFriend(Integer id, StatusFriends status);

    @Query("select u from User u join Friend f on u.id=f.secondUser.id where f.firstUser.id = ?1 and f.statusFriends = 'ACCEPTED'")
    User isMyFriend(Integer id);

}
