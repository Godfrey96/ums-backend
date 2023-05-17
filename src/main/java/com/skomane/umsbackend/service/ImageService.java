package com.skomane.umsbackend.service;

import com.skomane.umsbackend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    static String defaultPath = System.getProperty("user.dir");

    private final ImageRepository imageRepository;

    private static final File DIRECTORY = new File(defaultPath + "/img");
    private static final String URL = "http:://localhost:8081/images/";

}
