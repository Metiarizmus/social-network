package com.nikolai.network.service.interfaces;


import com.nikolai.network.dto.UserProfileDto;

public interface UserProfileService {
    void saveAvatar(byte[] avatar, String email);

    UserProfileDto getUserDto(String email);
}
