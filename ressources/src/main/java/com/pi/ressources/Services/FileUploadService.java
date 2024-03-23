package com.pi.ressources.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface FileUploadService {
    String uploadFile(MultipartFile file) throws IOException;




    //Optional<String> uploadFile(MultipartFile file) throws IOException;

   // void saveFile(String fileName, MultipartFile multipartFile) throws IOException;
}
