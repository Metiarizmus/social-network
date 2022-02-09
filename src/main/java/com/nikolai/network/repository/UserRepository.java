package com.nikolai.network.repository;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.Group;
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

    @Query("select u from User u join Friend f on  u.id=f.userFrom.id where f.userTo.id = ?1 and f.statusFriends = ?2")
    List<User> listIncomingRequest(Integer idUserTo, StatusFriends statusFriends);

    @Query(value = "select count(user.id) from user join group_user on user.id=group_user.user_id join associations on associations.id=group_user.associations_id where associations.id = ?1", nativeQuery = true)
    Integer countSubscribersInGroup(Integer id);

    //select * from user join group_user on user.id=group_user.user_id join associations on  associations.id=group_user.associations_id where associations.id=10;
    List<User> findAllByGroups(Group group);


}
