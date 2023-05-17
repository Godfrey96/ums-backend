package com.skomane.umsbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageId;

    @Column(name = "image_name", unique = true)
    private String imageName;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_path")
    @JsonIgnore
    private String imagePath;

    @Column(name = "image_url")
    private String imageURL;

    public Image(String imageName, String imageType, String imagePath, String imageURL) {
        this.imageName = imageName;
        this.imageType = imageType;
        this.imagePath = imagePath;
        this.imageURL = imageURL;
    }

    public Image(Integer imageId, String imageName, String imageType, String imagePath, String imageURL) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imagePath = imagePath;
        this.imageURL = imageURL;
    }
}
