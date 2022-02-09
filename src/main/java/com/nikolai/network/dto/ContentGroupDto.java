package com.nikolai.network.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ContentGroupDto implements Serializable {
    private Integer id;
    private String textContent;
    private byte[] fileContent;
    private String encodeBase64;
    private String time;
    private Date timeCompare;
    private GroupDto groupDto;
}
