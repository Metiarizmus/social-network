package com.nikolai.network.service.impl;

import com.nikolai.network.dto.ContentGroupDto;
import com.nikolai.network.model.ContentGroup;
import com.nikolai.network.model.Group;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.GroupContentRepository;
import com.nikolai.network.repository.GroupRepository;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl {

    @Autowired
    private GroupContentRepository groupContentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConvert dtoConvert;


    public List<ContentGroupDto> showContentGroupForUser(String email) {

        User user = userRepository.findByEmail(email);

        List<Group> userGroups = groupRepository.findByGroupUsers(user);
        List<ContentGroup> contentGroups = new ArrayList<>();
        List<ContentGroupDto> contentGroupDtos = new ArrayList<>();

        for (int i = 0; i < userGroups.size(); i++) {
            contentGroups.addAll(groupContentRepository.findByGroup(userGroups.get(i)));
        }

        for (ContentGroup q : contentGroups) {
            contentGroupDtos.add(dtoConvert.convertToContentDto(q));
        }

        return contentGroupDtos;

    }

}
