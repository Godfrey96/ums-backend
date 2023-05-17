package com.skomane.umsbackend.repository;

import com.skomane.umsbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    Optional<Image> findByImageName(String imageName);
}
