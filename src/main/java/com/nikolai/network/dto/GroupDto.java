package com.nikolai.network.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupDto implements Serializable {
    private Integer id;
    private String name;
    private byte[] avatar;
    private String encodeBase64;
    private Integer idCreater;
    private Integer countPeople;
    private String date;
}
