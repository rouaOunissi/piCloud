package com.pi.cours.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService implements IFileService {

    @Value("${uploads-directory}")
    private String UPLOAD_DIR;

    @Override
    public Optional<String> uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Optional.empty();
        }

        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            Path filePath = Paths.get("src/main/resources/", UPLOAD_DIR, fileName);

            Files.createDirectories(filePath.getParent());
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return Optional.of(fileName);
        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> convertFileToBase64(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return Optional.empty();
        }

        try {
            byte[] fileBytes = file.getBytes();
            String base64Content = Base64.getEncoder().encodeToString(fileBytes);
            return Optional.of(base64Content);
        } catch (IOException e) {
            log.error("Error converting file to Base64: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        return System.currentTimeMillis() + "_" + originalFileName;
    }
}
