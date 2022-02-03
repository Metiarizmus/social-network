package com.nikolai.network.repository;

import com.nikolai.network.dto.GroupDto;
import com.nikolai.network.model.Group;
import com.nikolai.network.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findByCreaterId(Integer id);

    @Query("SELECT g from Group g where g.nameGroup like %?1%")
    List<Group> search(String keyword);

    List<Group> findByGroupUsers(User user);
}
