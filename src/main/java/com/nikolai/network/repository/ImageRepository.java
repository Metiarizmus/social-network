package com.nikolai.network.repository;

import com.nikolai.network.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> getAllByUser_Email(String email);

    Image getByIdAndUser_Email(Long id, String userEmail);
}
