package com.nikolai.network.service.impl;

import com.nikolai.network.dto.ImageDto;
import com.nikolai.network.enums.TypeImage;
import com.nikolai.network.model.Image;
import com.nikolai.network.model.User;
import com.nikolai.network.repository.ImageRepository;
import com.nikolai.network.service.interfaces.GalleryService;
import com.nikolai.network.utils.DtoConvert;
import com.nikolai.network.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryServiceImpl extends BaseServiceImpl implements GalleryService {

    @Autowired
    private ImageUtils fileSystemService;

    @Autowired
    private ImageRepository imageDbRepository;

    @Autowired
    private DtoConvert dtoConvert;

    @Override
    public Integer saveImage(MultipartFile file, User user) throws IOException {
        Image image = new Image();

        String location = fileSystemService.save(file, user.getEmail());

        image.setUser(user);
        image.setLocation(location);
        image.setName(file.getOriginalFilename());
        image.setTypeImage(TypeImage.IMAGE);
        image.setDate(dateNow());

        return imageDbRepository.save(image).getId();
    }

    @Override
    public List<ImageDto> getAllImageForUser(String userEmail) {
        List<Image> images = imageDbRepository.getAllByUser_Email(userEmail);

        List<ImageDto> imageDtos = new ArrayList<>();

        for (Image q : images) {
            try {
                imageDtos.add(dtoConvert.convertToImageDto(q));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return imageDtos;
    }
}
