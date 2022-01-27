package com.nikolai.network.dto;

import com.nikolai.network.enums.TypeImage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDto {
    private String name;
    private String location;
    private TypeImage typeImage;
    private byte[] image;
    private String encodeBase64;


}
