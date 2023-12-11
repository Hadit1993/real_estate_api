package com.hadit1993.realestate.api.config;


import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Configuration
public class ImageUploader {

public String uploadImage(MultipartFile image, ImageType imageType) throws IOException {

    final String staticFolderPath = "src/main/resources/static/images" + imageType.getPath();
    final String extension = getFileExtension(Objects.requireNonNull(image.getOriginalFilename()));
    final String uniqueFileName = UUID.randomUUID() + "." + extension;
    final Path filePath = Paths.get(staticFolderPath,  "/" , uniqueFileName);
    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    final URI url = URI.create("http://localhost:8080/images" + imageType.getPath() + "/" + uniqueFileName);
    return url.toString();

}


private String getFileExtension(String fileName) {
     final int dotIndex = fileName.lastIndexOf('.');
     return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
}

    @Getter
    public enum ImageType {

        User("/products");
//        COFFEESHOP("/coffeeshops");

        private final String path;


        ImageType(String path) {
            this.path = path;
        }

    }
}
