package com.nikolai.network.repository;

import com.nikolai.network.model.ContentGroup;
import com.nikolai.network.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface GroupContentRepository extends JpaRepository<ContentGroup, Integer> {

    List<ContentGroup> findByGroup(Group group);

}
