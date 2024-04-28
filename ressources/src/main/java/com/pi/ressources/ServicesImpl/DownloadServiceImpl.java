package com.pi.ressources.ServicesImpl;

import com.pi.ressources.Dao.DownloadDao;
import com.pi.ressources.Dao.RessourceDao;
import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.Services.DownloadService;
import com.pi.ressources.entities.Download;
import com.pi.ressources.entities.Ressource;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.time.ZoneId;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public ResponseEntity<?> deteleDownload(Long id)
    {Optional <Download> download = downloadDao.findById(id);
        if(download.isPresent()){
            downloadDao.deleteById(id);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        else
            return ResponseEntity.badRequest().body("Download do not exist");
    }

   /**
    * Section Statistique
    */

   @Override
   public Long countDownloadsByDay(){
       return downloadDao.countDownloadsByDay();
   }
    @Override
    public Long countDownloadsByWeek(){
        return downloadDao.countDownloadsByWeek();
    }

   @Override
   public Map<String, Long> getLastWeekDownloads() {
       List<Download> downloads = this.downloadDao.findAll();
       Map<String, Long> lastWeekMap = new HashMap<>();

       LocalDate today = LocalDate.now();
       LocalDate lastWeekStart = today.minusWeeks(1).with(DayOfWeek.MONDAY);

       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

       for (int i = 0; i < 7; i++) {
           String date = lastWeekStart.plusDays(i).format(formatter);
           lastWeekMap.put(date, 0L);
       }

       for (Download download : downloads) {
           LocalDate downloadDate = download.getDateDownload().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
           String dateKey = downloadDate.format(formatter);
           if (lastWeekMap.containsKey(dateKey)) {
               lastWeekMap.put(dateKey, lastWeekMap.get(dateKey) + 1);
           }
       }

       return lastWeekMap;
   }

    @Override
    public Map<String, Long> getThisWeekDownloads() {
        List<Download> downloads = this.downloadDao.findAll();
        Map<String, Long> thisWeekMap = new HashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate thisWeekStart = today.with(DayOfWeek.MONDAY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        for (int i = 0; i < 7; i++) {
            String date = thisWeekStart.plusDays(i).format(formatter);
            thisWeekMap.put(date, 0L);
        }

        for (Download download : downloads) {
            LocalDate downloadDate = download.getDateDownload().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String dateKey = downloadDate.format(formatter);
            if (thisWeekMap.containsKey(dateKey)) {
                thisWeekMap.put(dateKey, thisWeekMap.get(dateKey) + 1);
            }
        }

        return thisWeekMap;
    }


    @Override
    public Long  countDownloadsByType(TypeRessource type){
        return downloadDao.countDownloadsByType(type);
    }


    @Override
    public List<Download> findAllByYear(int year) {
        return downloadDao.findAllByYear(year);
    }


    @Override
    public Map<String, Long> countDownloadsByMonth() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        // Obtenez une liste de tous les téléchargements pour l'année en cours
        List<Download> downloads = downloadDao.findAllByYear(currentYear);

        // Créer une carte pour stocker le nombre de téléchargements par mois
        Map<String, Long> downloadsByMonth = new HashMap<>();

        // Récupérer les noms des mois en français
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.FRENCH);
        String[] monthNames = symbols.getMonths();

        // Initialisez la carte avec des valeurs par défaut à zéro
        for (int i = 0; i < 12; i++) {
            downloadsByMonth.put(monthNames[i], 0L);
        }

        for (Download download : downloads) {
            Calendar downloadDate = Calendar.getInstance();
            downloadDate.setTime(download.getDateDownload());
            int month = downloadDate.get(Calendar.MONTH); // Les mois dans Calendar commencent à partir de 0
            String monthName = monthNames[month];
            Long count = downloadsByMonth.get(monthName);
            downloadsByMonth.put(monthName, count + 1);
        }

        return downloadsByMonth;
    }

    @Override
    public Ressource findMostDownloadedResource() {
        Long resourceId = downloadDao.findMostDownloadedResourceId();

        if (resourceId == null) {
            return null;
        }

        Optional<Ressource> optionalResource = ressourceDao.findById(resourceId);
        return optionalResource.orElse(null);
    }



}
