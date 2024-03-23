package com.pi.ressources.ServicesImpl;

import com.pi.ressources.Dao.DownloadDao;
import com.pi.ressources.Dao.RessourceDao;
import com.pi.ressources.Services.DownloadService;
import com.pi.ressources.entities.Download;
import com.pi.ressources.entities.Ressource;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class DownloadServiceImpl implements DownloadService {

    @Resource
    private DownloadDao downloadDao;

    @Resource
    private RessourceDao ressourceDao;



   @Override
    public List<Download> findAll()
    {
        return downloadDao.findAll();
    }

    @Override
    public long countDownloads() {
        return downloadDao.count();
    }


    @Override
    public List<Download> getByIdUser(Long userid){
        return downloadDao.findByUserId(userid);
    }

  /**
   * Lors du téléchargement de la ressource , le téléchargement sera ajouté à la BD*/
  @Override
    public Download addDownload(Download download) {
      download.setDateDownload(new Date());
      download.setIdUser(Long.valueOf(1));

      Ressource ressource = download.getRessource();
              Optional<Ressource> ressourceOptional = ressourceDao.findById(ressource.getIdRessource());
              if (ressourceOptional.isPresent()) {
                  ressource = ressourceOptional.get();
                  download.setRessource(ressource);
                  return this.downloadDao.save(download);
              } else {
                  return null;
              }
    }


    @Value("${uploads-directory}")
    private String UPLOAD_DIR;
    @Override
    public InputStream downloadFile(String fileName) throws FileNotFoundException {
        Path urlFile = Paths.get(UPLOAD_DIR, fileName);
        File file = urlFile.toFile();
        if (file.exists()) {
            return new FileInputStream(file);
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

    @Override
    public byte[] downloadFileContent(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        return Files.readAllBytes(filePath);
    }


   @Override
    public String deteleDownload(Long id)
    {
        downloadDao.deleteById(id);
        return "Download removed !!";
    }


}
