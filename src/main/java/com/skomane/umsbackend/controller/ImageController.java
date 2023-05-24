package com.skomane.umsbackend.controller;

import com.skomane.umsbackend.exceptions.UnableToResolvePhotoException;
import com.skomane.umsbackend.exceptions.UnableToSavePhotoException;
import com.skomane.umsbackend.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@CrossOrigin("*")
@Tag(name = "Image")
public class ImageController {

    private final ImageService imageService;

    @ExceptionHandler({UnableToResolvePhotoException.class, UnableToSavePhotoException.class})
    public ResponseEntity<String> handlePhotoException() {
        return new ResponseEntity<>("Unable to process the photo", HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String filename) throws UnableToResolvePhotoException {
        byte[] imageBytes = imageService.downloadImage(filename);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageService.getImageType(filename)))
                .body(imageBytes);
    }

}
