package com.pi.ressources.ServicesImpl;

import com.pi.ressources.Services.FileUploadService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Optional;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${uploads-directory}")
    private String UPLOAD_DIR;


    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is null or empty");
        }

        String fileName = generateUniqueFileName(file.getOriginalFilename());
        Path uploadDirectory = Paths.get(UPLOAD_DIR);
        Path urlFile = uploadDirectory.resolve(fileName);

        Files.createDirectories(uploadDirectory);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, urlFile, StandardCopyOption.REPLACE_EXISTING);
        } return fileName;
    }



    private String generateUniqueFileName(String originalFileName) {
        return System.currentTimeMillis() + "_" + originalFileName;
    }


}
