package com.nikolai.network.utils;

import com.nikolai.network.dto.*;
import com.nikolai.network.enums.StatusFriends;
import com.nikolai.network.model.ContentGroup;
import com.nikolai.network.model.Group;
import com.nikolai.network.model.Image;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.FriendRepository;
import com.nikolai.network.repository.UserRepository;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Component
public class DtoConvert {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    public UserRegistrDto convertToRegistrDto(User user) {
        return modelMapper.map(user, UserRegistrDto.class);
    }

    public UserDto convertToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setEncodeBase64(convertBinImageToString(userDto.getAvatar()));
        userDto.setFullName(user.getFirstName() + " " + user.getLastName());
        userDto.setDateRegistr(user.getDate());

        userDto.setCountRequestUser(userRepository.listIncomingRequest(user.getId(), StatusFriends.WAITING).size());
        userDto.setCountFriends(friendRepository.listMyFriends(user.getId()).size());
        userDto.setCountSubscribers(userRepository.listIncomingRequest(user.getId(), StatusFriends.IGNORED).size());

        return userDto;
    }

    public UserRequestDto convertToUserRequestDto(User user) {
        UserRequestDto userRequestDto = modelMapper.map(user, UserRequestDto.class);
        userRequestDto.setEncodeBase64(convertBinImageToString(userRequestDto.getAvatar()));
        userRequestDto.setFullName(user.getFirstName() + " " + user.getLastName());
        return userRequestDto;
    }

    public GroupDto convertToGroupDto(Group group) {
        GroupDto groupDto = modelMapper.map(group, GroupDto.class);
        groupDto.setEncodeBase64(convertBinImageToString(groupDto.getAvatar()));
        groupDto.setCountPeople(userRepository.countSubscribersInGroup(groupDto.getId()));
        groupDto.setDate(group.getDate());
        return groupDto;
    }

    @SneakyThrows
    public ContentGroupDto convertToContentDto(ContentGroup contentGroup) {
        ContentGroupDto content = modelMapper.map(contentGroup, ContentGroupDto.class);
        content.setTextContent(contentGroup.getContent());
        content.setEncodeBase64(convertBinImageToString(content.getFileContent()));
        content.setTime(contentGroup.getDate());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(contentGroup.getDate());
        content.setTimeCompare(date);


        return content;
    }


    public ImageDto convertToRegistrDto(Image image) throws UnsupportedEncodingException {

        BufferedImage bufferedImage = null;
        byte[] bytesImage = null;
        try {
            bufferedImage = ImageIO.read(new File(image.getLocation()));
            bytesImage = imageUtils.toByteArray(bufferedImage, "jpg");
        } catch (IOException e) {

        }

        ImageDto imageDto = modelMapper.map(image, ImageDto.class);
        imageDto.setImage(bytesImage);
        imageDto.setEncodeBase64(convertBinImageToString(imageDto.getImage()));

        return imageDto;
    }

    private static String convertBinImageToString(byte[] binImage) {
        if(binImage!=null && binImage.length>0) {
            return Base64.getEncoder().encodeToString(binImage);
        }
        else
            return "";
    }


}
