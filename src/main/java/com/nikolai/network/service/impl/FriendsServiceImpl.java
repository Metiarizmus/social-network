package com.nikolai.network.service.impl;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.dto.UserRequestDto;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.Friend;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.FriendRepository;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.FriendsService;
import com.nikolai.network.utils.DtoConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsServiceImpl extends BaseServiceImpl implements FriendsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

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
            userDtoList.add(dtoConvert.convertToUserDto(q));
        }

        return userDtoList;
    }

    @Override
    public boolean requestToFriend(Integer idFrom, Integer idTo) {
        User userFrom = userRepository.getById(idFrom);
        User userTo = userRepository.getById(idTo);

        if(friendRepository.findByUserFromAndUserTo(userFrom,userTo) != null){
            return false;
        }

        Friend friend = new Friend(StatusFriends.WAITING, userFrom, userTo);
        friend.setDate(dateNow());
        friendRepository.save(friend);
        return true;
    }

    @Override
    public void acceptedOrIgnoredFriend(Integer idFrom, Integer idTo, StatusFriends statusFriends) {

        User userFrom = userRepository.getById(idFrom);
        User userTo = userRepository.getById(idTo);

        Friend friend = friendRepository.findByUserFromAndUserTo(userFrom,userTo);
        friend.setStatusFriends(statusFriends);
        friend.setDate(dateNow());

        friendRepository.save(friend);
    }

    @Override
    public List<UserRequestDto> listUsersByStatus(Integer idTo, StatusFriends statusFriends) {

        List<User> list = userRepository.listIncomingRequest(idTo, statusFriends);

        List<UserRequestDto> userDtoList = new ArrayList<>();

        for (User q : list) {
            userDtoList.add(dtoConvert.convertToUserRequestDto(q));
        }

        return userDtoList;

    }

    @Override
    public List<UserRequestDto> listMyFriend(Integer idUser) {

        List<Friend> friendList = friendRepository.listMyFriends(idUser);

        List<UserRequestDto> userRequestDtoList = new ArrayList<>();

        for (Friend q : friendList) {
            if(q.getUserFrom().getId() == idUser){
                User user = userRepository.getById(q.getUserTo().getId());
                userRequestDtoList.add(dtoConvert.convertToUserRequestDto(user));
            }else if (q.getUserTo().getId() == idUser){
                User user = userRepository.getById(q.getUserFrom().getId());
                userRequestDtoList.add(dtoConvert.convertToUserRequestDto(user));
            }
        }

        return userRequestDtoList;
    }


}
