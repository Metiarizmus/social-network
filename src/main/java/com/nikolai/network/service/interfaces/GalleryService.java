package com.nikolai.network.service.interfaces;

import com.nikolai.network.dto.ImageDto;
import com.nikolai.network.model.Image;
import com.nikolai.network.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GalleryService {
    Integer saveImage(MultipartFile file, User user) throws IOException;

    List<ImageDto> getAllImageForUser(String userEmail);


}
