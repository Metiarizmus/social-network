package com.nikolai.network.service.interfaces;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.dto.UserRequestDto;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.User;

import java.util.List;

public interface FriendsService extends BaseService {

    List<UserDto> search(String keyword);

    boolean requestToFriend(Integer idFrom, Integer idTo);

    void acceptedOrIgnoredFriend(Integer idFrom, Integer idTo, StatusFriends statusFriends);

    List<UserRequestDto> listUsersByStatus(Integer idTo, StatusFriends statusFriends);

    List<UserRequestDto> listMyFriend(Integer idUser);
}
