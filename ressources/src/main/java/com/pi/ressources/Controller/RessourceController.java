package com.pi.ressources.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.ressources.Dao.ReactionDao;
import com.pi.ressources.Dao.RessourceDao;
import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.Services.FileUploadService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.ServicesImpl.PDFExtractorService;
import com.pi.ressources.entities.CalendarLine;
import com.pi.ressources.entities.Reaction;
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
import java.util.ArrayList;
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
    private ReactionDao reactionDao;
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


    @GetMapping("/search")
    public List<Ressource> searchRessourcesByTitre(@RequestParam String titre) {
        return ressourceService.findByTitreContaining(titre);
    }

    @GetMapping("/synonyms")
    public List<Ressource> searchRessourcesBySynonyms(@RequestParam String word) {
        return ressourceService.searchRessourcesBySynonyms(word);
    }


    @GetMapping("/synonymsSearch")
    public List<Ressource> searchRessourcesByKeyword(@RequestParam String word) {
        return ressourceService.searchRessourcesByKeyword(word);
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





    @PutMapping("/updateRess/{idRessource}")
    @ResponseBody
    public ResponseEntity<?> updateRessource(@PathVariable Long idRessource,HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            Ressource ressource = objectMapper.readValue(request.getParameter("ressource"), Ressource.class);

            Optional<Ressource> fileSave = ressourceService.updateRessource(idRessource, ressource, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileSave);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @DeleteMapping("/deleteRessByid/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteRessource (@PathVariable Long id)
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
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ressource avec l'ID spécifié n'a pas été trouvée.");
            }
            String contentType = ressource.getFileType().toLowerCase();
            String content;

            // Vérifier le type de fichier
            if ("pdf".equals(contentType)) {
                // Extraction du contenu d'un fichier PDF
                content = pdfExtractorService.extractTextFromPDF(new FileInputStream(new File("C:/xampp/htdocs/Files/" + ressource.getUrlFile())));
            } else if ("docx".equals(contentType)) {
                // Extraction du contenu d'un fichier DOCX
                XWPFDocument docx = new XWPFDocument(new FileInputStream(new File("C:/xampp/htdocs/Files/" + ressource.getUrlFile())));
                XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
                content = extractor.getText();
                extractor.close();
            } else {
                // Autres types de fichiers non pris en charge
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le type de fichier n'est pas pris en charge.");
            }

            return ResponseEntity.ok(content);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'extraction du contenu du fichier.");
        }
    }

    @GetMapping("/searchContent")
    public ResponseEntity<?> searchContent(@RequestParam String keyword) {
        try {
            List<Ressource> matchingRessources = new ArrayList<>();

            // Récupérer toutes les ressources (par exemple)
            List<Ressource> allRessources = ressourceService.findAll();

            // Parcourir chaque ressource pour extraire et rechercher le contenu
            for (Ressource ressource : allRessources) {
                String contentType = ressource.getFileType().toLowerCase();
                String content;

                // Vérifier le type de fichier
                if ("pdf".equals(contentType)) {
                    // Extraction du contenu d'un fichier PDF
                    content = pdfExtractorService.extractTextFromPDF(new FileInputStream(new File("C:/xampp/htdocs/Files/" + ressource.getUrlFile())));

                    // Recherche du mot-clé dans le contenu extrait
                    if (content != null && content.contains(keyword)) {
                        matchingRessources.add(ressource);
                    }
                }


            }

            return ResponseEntity.ok(matchingRessources);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la recherche dans le contenu des fichiers.");
        }
    }

    @PutMapping("/reactToRessource/{id}")
    @ResponseBody
    public ResponseEntity<?> reactToRessource(@PathVariable Long id)
    {
        return this.ressourceService.reactToRessource(id);
    }

    @GetMapping("/findReactionByIdReactionAndIdUser/{idRess}/{idUser}")
    @ResponseBody
    public Reaction findReactionByIdReactionAndIdUser(@PathVariable Long idRess, @PathVariable Long idUser)
    {
        return reactionDao.findReactionByIdReactionAndIdUser(idRess,idUser);
    }

    @GetMapping("/hasUserReactedToResource/{idRess}/{idUser}")
    @ResponseBody
    public boolean hasUserReactedToResource(@PathVariable Long idRess, @PathVariable Long idUser)
    {
        return ressourceService.hasUserReactedToResource(idRess,idUser);
    }


    @GetMapping("/getRessourcesOrderedByNbrReact")
    @ResponseBody
    public List<Ressource> getRessourcesOrderedByNbrReactDesc()
    {
        return ressourceService.getRessourcesOrderedByNbrReactDesc();
    }



}







