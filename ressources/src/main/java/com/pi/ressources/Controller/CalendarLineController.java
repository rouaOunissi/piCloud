package com.pi.ressources.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.ressources.Services.CalendarLineService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.ServicesImpl.PDFExtractorService;
import com.pi.ressources.entities.CalendarLine;
import com.pi.ressources.entities.Event;
import com.pi.ressources.entities.Ressource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Controller
@RequestMapping("/api/v1/calendarLine/")
@CrossOrigin(origins = "http://localhost:4200")

public class CalendarLineController {



    @Autowired
    private CalendarLineService calendarLineService;


    @GetMapping("/getCalendarLine")
    @ResponseBody
    public List<CalendarLine> getCalendarLine()
    {
        return this.calendarLineService.findAll();
    }

    @GetMapping("/getCalendarLineByid/{id}")
    @ResponseBody
    public CalendarLine getCalendarLineByid(@PathVariable Long id)
    {
        return this.calendarLineService.findById(id);
    }

    @GetMapping("/getCalendarPdf/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> getCalendarPdf(@PathVariable Long id)
    {
        return this.calendarLineService.getCalendarPdf(id);
    }



    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/uploadCalendarLineData")
    @ResponseBody
    public ResponseEntity<?> uploadCalendarLineData(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            CalendarLine calendarLine = objectMapper.readValue(multipartRequest.getParameter("calendarLine"), CalendarLine.class);

            Optional<CalendarLine> fileSave = calendarLineService.addCalendarLineFile(calendarLine, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileSave);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @PutMapping("/update/{idCalendarLine}")
    @ResponseBody
    public ResponseEntity<?> updateCalendarLine(@PathVariable Long idCalendarLine,HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            CalendarLine calendarLine = objectMapper.readValue(multipartRequest.getParameter("calendarLine"), CalendarLine.class);

            Optional<CalendarLine> fileSave = calendarLineService.UpCalendarLineFile(idCalendarLine,calendarLine, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileSave);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }



    @DeleteMapping("/deleteCalendarLine/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRessource (@PathVariable Long id)
    {return this.calendarLineService.deteleCalendarLine(id);}

    @GetMapping("/listeSpecialites")
    @ResponseBody
    public List<String> getAllSpecialiteNames()
    {
        return calendarLineService.getAllSpecialiteNames();
    }


    @Autowired
    private PDFExtractorService pdfExtractorService;



    public static String extractTextFromPdf(InputStream inputStream) {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper textStripper = new PDFTextStripper();
            return textStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/extractContentCalendar/{idCalendarLine}")
    public ResponseEntity<?> extractContentCalendar(@PathVariable Long idCalendarLine) {
        try {
            CalendarLine calendarLine = calendarLineService.findById(idCalendarLine);
            if (calendarLine == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Calendrier avec l'ID spécifié n'a pas été trouvé.");
            }
            String contentType = calendarLine.getFileType().toLowerCase();
            String content = null;

            // Vérifier le type de fichier
            if ("pdf".equals(contentType)) {
                // Ouvrir le fichier PDF et extraire le contenu
                try (InputStream inputStream = new FileInputStream(new File("C:/xampp/htdocs/Files/" + calendarLine.getUrlFile()))) {
                    content = this.extractTextFromPdf(inputStream);
                }
            } else if ("docx".equals(contentType)) {
                // Extraction du contenu d'un fichier DOCX (si nécessaire)
                // ...
            } else {
                // Autres types de fichiers non pris en charge
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le type de fichier n'est pas pris en charge.");
            }

            // Retourner la réponse avec le contenu extrait
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'extraction du contenu du fichier.");
        }
    }




    // Méthode pour identifier les événements à partir du texte extrait
    @GetMapping("/identifyEvents")
    @ResponseBody
    public static List<Event> identifyEvents(String text) {
        List<Event> events = new ArrayList<>();
        String[] lines = text.split("\n");
        String currentMonth = "";

        for (String line : lines) {
            Pattern pattern = Pattern.compile("\\b\\d+\\s+[A-Z]\\b");
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                String date = matcher.group();
                String[] dateParts = date.split("\\s+");
                String day = dateParts[0];
                String monthAbbreviation = dateParts[1];

                // Convertir l'abréviation du mois en nom complet du mois
                String month = convertAbbreviationToMonth(monthAbbreviation);

                if (!month.equals(currentMonth)) {
                    // Nouveau mois détecté, mettre à jour le mois en cours
                    currentMonth = month;
                    events.add(new Event(day + " " + currentMonth, "Début du mois de " + currentMonth));
                }

                // Récupérer le titre de l'événement après la date
                int endIndex = matcher.end();
                String eventTitle = line.substring(endIndex).trim();
                events.add(new Event(date, eventTitle));
            }
        }

        return events;
    }


    public static String convertAbbreviationToMonth(String abbreviation) {
        switch (abbreviation) {
            case "J":
                return "Janvier";
            case "F":
                return "Février";
            case "M":
                return "Mars";
            // Ajoutez d'autres cas pour les autres mois
            default:
                return "";
        }
    }



}
