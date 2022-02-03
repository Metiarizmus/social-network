package com.nikolai.network.service.impl;

import com.nikolai.network.dto.GroupDto;
import com.nikolai.network.dto.UserDto;
import com.nikolai.network.dto.UserRequestDto;
import com.nikolai.network.model.Group;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.GroupRepository;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GroupServiceImpl extends BaseServiceImpl{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConvert dtoConvert;

    public void creatGroup(Group group, User user) {
        group.setDate(dateNow());
        group.setGroupUsers(Collections.singleton(user));
        groupRepository.save(group);
    }

    public List<GroupDto> listMyControllingGroups(Integer id) {

        List<GroupDto> groupDtos = new ArrayList<>();

        for (Group q : groupRepository.findByCreaterId(id)){
            groupDtos.add(dtoConvert.convertToGroupDto(q));
        }

        return groupDtos;
    }

    public GroupDto adminModeGroup(Group group) {
        return dtoConvert.convertToGroupDto(group);
    }

    public List<GroupDto> findNewGroup(String keyword){
        List<Group> groups = new ArrayList<>();

        if(keyword != null) {
            groups.addAll(groupRepository.search(keyword));
        }
        List<GroupDto> groupDtos = new ArrayList<>();

        for (Group q : groups){
            groupDtos.add(dtoConvert.convertToGroupDto(q));
        }

        return groupDtos;

    }

    public List<GroupDto> listMyGroups(User user){

        List<GroupDto> list = new ArrayList<>();

        for (Group q : groupRepository.findByGroupUsers(user)){
            list.add(dtoConvert.convertToGroupDto(q));
        }

        return list;
    }

    public List<UserRequestDto> listSubscribersUserForGroup(Group group) {

        List<UserRequestDto> list = new ArrayList<>();

        for (User q : userRepository.findAllByGroups(group)) {
            list.add(dtoConvert.convertToUserRequestDto(q));
        }

        return list;

    }

}
