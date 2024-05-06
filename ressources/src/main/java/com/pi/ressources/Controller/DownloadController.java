package com.pi.ressources.Controller;

import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.Services.DownloadService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.entities.Download;
import com.pi.ressources.entities.Ressource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.io.Files.getFileExtension;

@Slf4j
@RestController
@Controller
@RequestMapping("/api/v1/download/")
@CrossOrigin(origins = "http://localhost:4200")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;



    @GetMapping("/getDownloads")
    @ResponseBody
    public List<Download> getDownloads()
    {
        return this.downloadService.findAll();
    }

    @GetMapping("/countDownloads")
    @ResponseBody
    public long countDownloads() {
        return downloadService.countDownloads();
    }


    @GetMapping("/getDownloadsByidUser/{userid}")
    @ResponseBody
    public List<Download> getByIdUser(@PathVariable Long userid)
    {
        return this.downloadService.getByIdUser(userid);
    }


    @DeleteMapping("/deleteDownByid/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteDownload (@PathVariable Long id)
    {return this.downloadService.deteleDownload(id);}



    @PostMapping("/addDownload")
    @ResponseBody
    public Download addDownload(@RequestBody Download download)
    {
        return download !=null ? this.downloadService.addDownload(download) : null;
    }


    @Autowired
    private RessourceService ressourceService;

    @GetMapping("/download/{urlFile}/{fileName}/{idUser}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(@PathVariable String urlFile, @PathVariable String fileName, @PathVariable Long idUser) {
        try {

            byte[] fileContent = downloadService.downloadFileContent(urlFile);

            HttpHeaders headers = new HttpHeaders();

            String contentType = determineContentType(urlFile);
            headers.setContentType(MediaType.parseMediaType(contentType));

            headers.setContentDispositionFormData("attachment", fileName);


            Ressource ressource = ressourceService.getByUrlFile(urlFile);
            if (ressource != null) {

                Download download = new Download();
                download.setRessource(ressource);
                download.setIdUser(idUser);
                download.setDateDownload(new Date());
                downloadService.addDownload(download);
            }

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private String extractFileNameFromUrl(String url) {
        // Extraire le nom du fichier de l'URL
        int lastSlashIndex = url.lastIndexOf("/");
        if (lastSlashIndex != -1) {
            return url.substring(lastSlashIndex + 1);
        } else {
            return "unknown";
        }
    }

    private String determineContentType(String fileName) {
        String extension = getFileExtension(fileName);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            default:
                return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }

    /**
     * Section Statistique
     */

    @GetMapping("/getDownloadsByDay")
    @ResponseBody
    public Long getDownloadsByDay()
    {
        return this.downloadService.countDownloadsByDay();
    }

    @GetMapping("/countDownloadsByWeek")
    @ResponseBody
    public Long countDownloadsByWeek()
    {
        return this.downloadService.countDownloadsByWeek();
    }


    @GetMapping("/getLastWeekDownloads")
    @ResponseBody
    public Map<String, Long> getLastWeekDownloads()
    {
        return this.downloadService.getLastWeekDownloads();
    }

    @GetMapping("/getThisWeekDownloads")
    @ResponseBody
    public Map<String, Long> getThisWeekDownloads()
    {
        return this.downloadService.getThisWeekDownloads();
    }
    @GetMapping("/countDownloadsByType/{typeRessource}")
    @ResponseBody
    public Long countDownloadsByType(@PathVariable TypeRessource typeRessource)
    {
        return this.downloadService.countDownloadsByType(typeRessource);
    }

    @GetMapping("/totalDownloadsOfTypeExam")
    @ResponseBody
    public Long totalDownloadsOfTypeExam()
    {
        return this.downloadService.countDownloadsByType(TypeRessource.Examen);
    }
    @GetMapping("/totalDownloadsOfTypePosit")
    @ResponseBody
    public Long totalDownloadsOfTypePosit()
    {
        return this.downloadService.countDownloadsByType(TypeRessource.Posit);
    }

    @GetMapping("/totalDownloadsOfTypeTD")
    @ResponseBody
    public Long totalDownloadsOfTypeTD()
    {
        return this.downloadService.countDownloadsByType(TypeRessource.TD);
    }

    @GetMapping("/totalDownloadsOfTypeTp")
    @ResponseBody
    public Long totalDownloadsOfTypeTp()
    {
        return this.downloadService.countDownloadsByType(TypeRessource.Tp);
    }

    @GetMapping("/totalDownloadsOfTypeRapport")
    @ResponseBody
    public Long totalDownloadsOfTypeRapport()
    {
        return this.downloadService.countDownloadsByType(TypeRessource.Rapport);
    }

    @GetMapping("/countDownloadsByMonth")
    @ResponseBody
    public Map<String, Long> countDownloadsByMonth()
    {
        return this.downloadService.countDownloadsByMonth();
    }

    @GetMapping("/MostDownloadedResource")
    @ResponseBody
    public Ressource findMostDownloadedResource()
    {
        return this.downloadService.findMostDownloadedResource();
    }


}
