package com.nikolai.network.service.impl;

import com.nikolai.network.dto.UserDto;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.FriendRepository;
import com.nikolai.network.repository.UserRepository;
import com.nikolai.network.service.interfaces.UserProfileService;
import com.nikolai.network.utils.DtoConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl extends BaseServiceImpl implements UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoConvert dtoConvert;


    @Override
    public void saveAvatar(byte[] avatar, String email) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        System.out.println(avatar);
        user.get().setAvatar(avatar);
        logger.info("save avatar for user with email :: " + email);
        userRepository.save(user.get());
    }


    @Override
    public UserDto findById(Integer id) {
        Optional<User> user = userRepository.findById(id);

        return dtoConvert.convertToUserDto(user.get());
    }


}
