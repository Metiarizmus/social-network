package com.nikolai.network.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String fullName;
    private String firstName;
    private String lastName;
    private String birthday;
    private byte[] avatar;
    private String encodeBase64;
    private String dateRegistr;
    private String email;

}
