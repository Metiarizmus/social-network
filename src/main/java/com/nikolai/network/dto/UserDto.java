package com.nikolai.network.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String fullName;
    private String birthday;
    private byte[] avatar;
    private String encodeBase64;
    private String dateRegistr;
    private String email;

    private Integer countRequestUser;
    private Integer countMessages;
    private Integer countFriends;
    private Integer countSubscribers;
    private boolean isYourFriend = false;
}
