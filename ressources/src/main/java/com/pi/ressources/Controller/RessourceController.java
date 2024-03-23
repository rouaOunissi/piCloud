package com.pi.ressources.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi.ressources.Dao.RessourceDao;
import com.pi.ressources.Enum.TypeRessource;
import com.pi.ressources.Services.FileUploadService;
import com.pi.ressources.Services.RessourceService;
import com.pi.ressources.entities.Ressource;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@RestController
@Controller
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



    @PutMapping("/upadet/{idRessource}")
    @ResponseBody
    public Ressource updateRessource(@PathVariable Long idRessource ,@RequestBody Ressource ressource)
    {
        return this.ressourceService.updateRessource(idRessource , ressource);
    }


    @DeleteMapping("/deleteRessByid/{id}/{idUser}")
    @ResponseBody
    public String deleteRessource (@PathVariable Long id, @PathVariable Long idUser)
    {return this.ressourceService.deteleRessource(id,idUser);}


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



}







