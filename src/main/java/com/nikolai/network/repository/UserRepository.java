package com.nikolai.network.repository;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("SELECT u from User u where concat(u.firstName,u.lastName) like %?1%")
    List<User> search(String keyword);

}
