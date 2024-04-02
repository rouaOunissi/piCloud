package com.pi.ressources.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.ressources.Dao.RessourceDao;
import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.Services.FileUploadService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.ServicesImpl.PDFExtractorService;
import com.pi.ressources.entities.Ressource;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
@Slf4j
@RestController
@Controller
@RequestMapping("/api/v1/ressource/")
@CrossOrigin(origins = "http://localhost:4200")
public class RessourceController {


    @Autowired
    private RessourceService ressourceService;


    @Resource
    private RessourceDao ressourceDao;

    @PostMapping("/addRessource")
    @ResponseBody
    public Ressource addRessource(@RequestBody Ressource ressource)
    {
        return ressource !=null ? this.ressourceService.addRessource(ressource) : null;
    }


    @GetMapping("/getRessource")
    @ResponseBody
    public List<Ressource> getRessource()
    {
        return this.ressourceService.findAll();
    }


    @GetMapping("/getRessourceByid/{id}")
    @ResponseBody
    public Ressource getRessource(@PathVariable Long id)
    {
        return this.ressourceService.findById(id);
    }


  /**
   * get by iduser  les ressources*/
    @GetMapping("/getRessourceByidUser/{userid}")
    @ResponseBody
    public List<Ressource> getByIdUser(@PathVariable Long userid)
    {
        return this.ressourceService.getByIdUser(userid);
    }



    @GetMapping("/ressourceByType")
    @ResponseBody
    public List<Ressource> getRessourcesByType(@RequestParam TypeRessource typeRessource)
    {
        return ressourceService.getRessourcesByType(typeRessource);
    }


    @GetMapping("/listeTypeRess")
    @ResponseBody
    public List<String> getAllTypeRessourceNames()
    {
        return ressourceService.getAllTypeRessourceNames();
    }



    @PutMapping("/update/{idRessource}")
    @ResponseBody
    public Ressource updateRessource(@PathVariable Long idRessource ,@RequestBody Ressource ressource)
    {
        return this.ressourceService.updateRessource(idRessource , ressource);
    }


    @DeleteMapping("/deleteRessByid/{id}")
    @ResponseBody
    public String deleteRessource (@PathVariable Long id)
    {return this.ressourceService.deteleRessource(id);}


    /***
     * Partie upload
     *
     */




    @Autowired
    FileUploadService fileUploadService;



  @PostMapping("/uploadRessFile")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {
      try {

          String fileName = fileUploadService.uploadFile(uploadfile);

          if (fileName != null) {
              String fileType = getFileExtension(uploadfile.getOriginalFilename());
              Ressource ressource = new Ressource();
              ressource.setFileName(String.valueOf(fileName));
              ressource.setFileType(fileType);
              ressource.setUrlFile(String.valueOf(fileName));
              Ressource savedRessource = ressourceService.addRessource(ressource);

              return ResponseEntity.status(HttpStatus.CREATED).body(savedRessource);
          } else {

              return ResponseEntity.badRequest().body("Failed to save file");
          }
      } catch (Exception e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
      }
  }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }


    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/uploadRessData")
    @ResponseBody
    public ResponseEntity<?> uploadRessourceData(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            Ressource ressource = objectMapper.readValue(multipartRequest.getParameter("ressource"), Ressource.class);

            Optional<Ressource> fileSave = ressourceService.addRessourceFile(ressource, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileSave);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/getRessourceContent")
    public ResponseEntity<String> getRessourceContent(@RequestParam String url) {
        try {

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {

                return ResponseEntity.ok(response.getBody());
            } else {

                return ResponseEntity.status(response.getStatusCode()).body("Failed to fetch resource content");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/generateInvoicePDF/{id}")
    public ResponseEntity<String> generateInvoicePDF(@PathVariable Long id) {
        try {

            String pdfUrl = ressourceService.GenerateInvoicePDF(id);

            return ResponseEntity.ok(pdfUrl);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while generating PDF invoice");
        }
    }

    @Autowired
    private PDFExtractorService pdfExtractorService;

    @GetMapping("/extractContent/{idRessource}")
    public ResponseEntity<?> extractContent(@PathVariable Long idRessource) {
        try {
            Ressource ressource = ressourceService.findById(idRessource);
            if (ressource == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"La ressource avec l'ID spécifié n'a pas été trouvée.\"}");
            }
            String contentType = ressource.getFileType().toLowerCase();
            String content;


            if ("pdf".equals(contentType)) {

                content = pdfExtractorService.extractTextFromPDF(new FileInputStream(new File("C:/xampp/htdocs/Files/" + ressource.getUrlFile())));
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body("{\"content\": \"" + content + "\"}");
            } else if ("docx".equals(contentType)) {

                XWPFDocument docx = new XWPFDocument(new FileInputStream(new File("C:/xampp/htdocs/Files/" + ressource.getUrlFile())));
                XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
                content = extractor.getText();
                extractor.close();
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("{\"content\": \"" + content + "\"}");
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Le type de fichier n'est pas pris en charge.\"}");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Erreur lors de l'extraction du contenu du fichier.\"}");
        }
    }


}







