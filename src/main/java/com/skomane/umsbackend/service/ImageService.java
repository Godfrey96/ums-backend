package com.skomane.umsbackend.service;

import com.skomane.umsbackend.exceptions.UnableToResolvePhotoException;
import com.skomane.umsbackend.exceptions.UnableToSavePhotoException;
import com.skomane.umsbackend.model.Image;
import com.skomane.umsbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    static String defaultPath = System.getProperty("user.dir");

    private final ImageRepository imageRepository;

    private static final File DIRECTORY = new File(defaultPath + "/img");
    private static final String URL = "http:://localhost:8081/images/";

    public Image uploadImage(MultipartFile file, String prefix) throws UnableToSavePhotoException {
        try {
            String extension = "." + file.getContentType().split("/")[1];
            File img = File.createTempFile(prefix, extension, DIRECTORY);
            file.transferTo(img);
            String imageURL = URL + img.getName();
            Image i = new Image(img.getName(), file.getContentType(), img.getPath(), imageURL);
            Image saved = imageRepository.save(i);
            return saved;
        } catch (Exception e) {
            throw new UnableToSavePhotoException();
        }
    }

    public byte[] downloadImage(String filename) throws UnableToResolvePhotoException {
        try {
            Image image = imageRepository.findByImageName(filename).get();
            String filePATH = image.getImagePath();
            byte[] imageBytes = Files.readAllBytes(new File(filePATH).toPath());
            return imageBytes;
        } catch (IOException e) {
            throw new UnableToResolvePhotoException();
        }
    }

    public String getImageType(String filename) {
        Image image = imageRepository.findByImageName(filename).get();
        return image.getImageType();
    }

}
