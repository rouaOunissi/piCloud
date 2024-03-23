package com.pi.ressources.Services;

import com.pi.ressources.entities.Download;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DownloadService {
    List<Download> findAll();

    long countDownloads();

    List<Download> getByIdUser(Long userid);

    Download addDownload(Download download);

    InputStream downloadFile(String fileName) throws FileNotFoundException;

    byte[] downloadFileContent(String fileName) throws IOException;

    String deteleDownload(Long id);
}
