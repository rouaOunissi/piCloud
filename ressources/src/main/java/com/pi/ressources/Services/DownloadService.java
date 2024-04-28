package com.pi.ressources.Services;

import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.entities.Download;
import com.pi.ressources.entities.Ressource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface DownloadService {
    List<Download> findAll();

    long countDownloads();

    List<Download> getByIdUser(Long userid);


    Long countDownloadsByDay();

    Long countDownloadsByWeek();

    Long  countDownloadsByType(TypeRessource type);

    Download addDownload(Download download);

    InputStream downloadFile(String fileName) throws FileNotFoundException;

    byte[] downloadFileContent(String fileName) throws IOException;


    ResponseEntity<?> deteleDownload(Long id);


    Map<String, Long> getLastWeekDownloads();

    Map<String, Long> getThisWeekDownloads();

    List<Download> findAllByYear(int year);

    Map<String, Long> countDownloadsByMonth();

    Ressource findMostDownloadedResource();
}
