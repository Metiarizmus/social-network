package com.nikolai.network.repository;

import com.nikolai.network.dto.GroupDto;
import com.nikolai.network.model.ContentGroup;
import com.nikolai.network.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupContentRepository extends JpaRepository<ContentGroup, Integer> {

    List<ContentGroup> findByGroup(Group group);

}
