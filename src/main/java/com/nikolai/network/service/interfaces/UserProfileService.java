package com.nikolai.network.service.interfaces;


import com.nikolai.network.dto.UserDto;

public interface UserProfileService {
    void saveAvatar(byte[] avatar, String email);

    UserDto getUserDto(String email);
}
