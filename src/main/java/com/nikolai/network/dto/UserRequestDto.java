package com.nikolai.network.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private Integer id;
    private String fullName;
    private byte[] avatar;
    private String encodeBase64;
}
