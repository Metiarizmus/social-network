package com.nikolai.network.service.interfaces;

import com.nikolai.network.dto.UserDto;

import java.util.List;

public interface FriendsService {

    List<UserDto> search(String keyword);

}
