package com.nikolai.network.service.interfaces;


import com.nikolai.network.dto.UserDto;

public interface UserProfileService extends BaseService{
    void saveAvatar(byte[] avatar, String email);

    UserDto findById(Integer id);
}
