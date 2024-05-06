package com.pi.cours.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IFileService {
    Optional<String> uploadFile(MultipartFile file);

    Optional<String> convertFileToBase64(MultipartFile file);
}
