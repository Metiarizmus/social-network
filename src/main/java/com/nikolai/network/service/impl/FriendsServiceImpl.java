package com.nikolai.network.service.impl;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.FriendsService;
import com.nikolai.network.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConvert dtoConvert;

    @Override
    public List<UserDto> search(String keyword) {
        List<User> list = new ArrayList<>();

        if(keyword != null) {
            list.addAll(userRepository.search(keyword));
        }

        List<UserDto> userDtoList = new ArrayList<>();

        for (User q : list) {
            userDtoList.add(dtoConvert.convertToDtoForUserDto(q));
        }

        return userDtoList;
    }
}
