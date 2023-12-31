package com.cg.inventoryspecialitiesservice.service.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUtils {

    public static String saveAvatar(MultipartFile file, String location, HttpServletRequest request) throws IOException {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String fileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path path = Paths.get(location);
        Files.createDirectories(path);
        File saveFile = Paths.get(location, fileName).toFile();
        file.transferTo(saveFile);
        return baseUrl + "/img/" + fileName;
    }
}
