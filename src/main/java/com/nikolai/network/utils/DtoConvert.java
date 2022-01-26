package com.nikolai.network.utils;

import com.nikolai.network.dto.UserProfileDto;
import com.nikolai.network.dto.UserRegistrDto;
import com.nikolai.network.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Component
public class DtoConvert {
    @Autowired
    private ModelMapper modelMapper;

    public UserRegistrDto convertToDto(User user) {
        return modelMapper.map(user, UserRegistrDto.class);
    }

    public UserProfileDto convertToDtoForProfile(User user) {
        UserProfileDto userProfileDto = modelMapper.map(user,UserProfileDto.class);
        userProfileDto.setEncodeBase64(convertBinImageToString(userProfileDto.getAvatar()));
        userProfileDto.setFullName(userProfileDto.getFirstName() + " " + userProfileDto.getLastName());
        userProfileDto.setDateRegistr(user.getDate());
        return userProfileDto;
    }

    private static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0) {
            return Base64.getEncoder().encodeToString(binImage);
        }
        else
            return "";
    }


}
