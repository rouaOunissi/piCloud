package com.pi.ressources.ServicesImpl;

import com.pi.ressources.Dao.CalendarLineDao;
import com.pi.ressources.Enum.Specialite;
import com.pi.ressources.Services.CalendarLineService;
import com.pi.ressources.Services.FileUploadService;
import com.pi.ressources.entities.CalendarLine;
import com.pi.ressources.entities.Event;
import jakarta.annotation.Resource;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.io.File;

import static com.google.common.io.Files.getFileExtension;

@Service
public class CalendarLineServiceImpl implements CalendarLineService {

    @Resource
    CalendarLineDao calendarLineDao;

    @Resource
    FileUploadService fileUploadService;


    @Override
    public List<CalendarLine> findAll()
    {
        return calendarLineDao.findAll();
    }

    @Override
    public CalendarLine findById(Long id){
        final Optional<CalendarLine > optionalCalendarLine=calendarLineDao.findById(id);
        if (optionalCalendarLine.isPresent())
        {
            return optionalCalendarLine.get();
        }
        return null;
    }

    @Override
    public List<String> getAllSpecialiteNames() {
        return Arrays.stream(Specialite.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CalendarLine> addCalendarLineFile(CalendarLine calendarLine, MultipartFile file) {
        try {
            String UrlFile = fileUploadService.uploadFile(file);
            if (UrlFile !=null)
            {   String fileName = file.getOriginalFilename();
                String fileType = getFileExtension(fileName);


                calendarLine.setFileName(fileName);
                calendarLine.setFileType(fileType);
                calendarLine.setAnneeUniv(calendarLine.getAnneeUniv());
                calendarLine.setSpecialite(calendarLine.getSpecialite());
                calendarLine.setDateCreation(new Date());
                calendarLine.setIdUser(Long.valueOf(1));
                calendarLine.setUrlFile(UrlFile);

                return Optional.of(this.calendarLineDao.save(calendarLine));

            }
            return Optional.empty();
        }catch (Exception ee)
        {
            ee.printStackTrace();
            return Optional.empty();
        }
    }


    @Override
    public Optional<CalendarLine> UpCalendarLineFile(Long idCalendarLine, CalendarLine calendarLine, MultipartFile file) {
        final Optional<CalendarLine> optionalCalendarLine = calendarLineDao.findById(idCalendarLine);
        try {
            if (file != null && !file.isEmpty()) {
                String UrlFile = fileUploadService.uploadFile(file);
                if (UrlFile != null) {
                    String fileName = file.getOriginalFilename();
                    String fileType = getFileExtension(fileName);

                    CalendarLine existingCalendarLine = optionalCalendarLine.orElseThrow(() -> new IllegalArgumentException("CalendarLine not found"));

                    existingCalendarLine.setAnneeUniv(calendarLine.getAnneeUniv());
                    existingCalendarLine.setFileName(fileName);
                    existingCalendarLine.setFileType(fileType);
                    existingCalendarLine.setSpecialite(calendarLine.getSpecialite());
                    existingCalendarLine.setUrlFile(UrlFile);

                    return Optional.of(this.calendarLineDao.save(existingCalendarLine));
                }
            } else {

                CalendarLine existingCalendarLine = optionalCalendarLine.orElseThrow(() -> new IllegalArgumentException("CalendarLine not found"));

                existingCalendarLine.setAnneeUniv(calendarLine.getAnneeUniv());
                existingCalendarLine.setSpecialite(calendarLine.getSpecialite());

                return Optional.of(this.calendarLineDao.save(existingCalendarLine));
            }
            return Optional.empty();
        } catch (Exception ee) {
            ee.printStackTrace();
            return Optional.empty();
        }
    }



    @Override
    public List<CalendarLine> getByIdUser(Long userid){
        return calendarLineDao.findByUserId(userid);
    }


    @Override
    public ResponseEntity<?> deteleCalendarLine(Long id)
    {Optional <CalendarLine> calendarLine = calendarLineDao.findById(id);
        if(calendarLine.isPresent()){
            calendarLineDao.deleteById(id);
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        }
        else
            return ResponseEntity.badRequest().body("Download do not exist");
    }



    @Override
    public ResponseEntity<byte[]> getCalendarPdf(Long id) {
        Optional<CalendarLine> optionalCalendarLine = calendarLineDao.findById(id);
        if (optionalCalendarLine.isPresent()) {
            CalendarLine calendarLine = optionalCalendarLine.get();
            String pdfFilePath = calendarLine.getUrlFile(); // Chemin du fichier PDF dans la base de données

            try {
                // Lire le fichier PDF et le convertir en tableau de bytes
                File pdfFile = new File(pdfFilePath);
                byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());

                // Renvoyer le contenu du fichier PDF dans la réponse
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=" + pdfFile.getName())
                        .body(pdfBytes);
            } catch (IOException e) {
                e.printStackTrace();
                // Gérer l'erreur de lecture du fichier PDF
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            // Gérer le cas où la ligne de calendrier avec l'ID spécifié n'est pas trouvée
            return ResponseEntity.notFound().build();
        }
    }





    public static void extractEvents(String text) {
        Pattern datePattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}"); // Pattern for date (e.g., 12/31/2023)
        Matcher matcher = datePattern.matcher(text);

        while (matcher.find()) {
            String eventDate = matcher.group(); // Date found in the text
            // Logic to process the event date
        }
    }





}



