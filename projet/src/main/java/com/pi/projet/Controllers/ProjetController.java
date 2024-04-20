package com.pi.projet.Controllers;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.RequestProjet2;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.Services.ProjetService;
import com.pi.projet.entities.Projet;
import com.pi.projet.feign.UserClient;
import com.pi.projet.repositories.ProjetRepo;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projets")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*") // Allows cross-origin requests from your Angular app

public class ProjetController {

    private final ProjetService projetService;
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody RequestProjet project) {
        return  projetService.createProject(project);
    }

    @GetMapping("/cat/{category_id}")
    public ResponseEntity<List<ResponseProjet>> getProjectByCategory(@PathVariable("category_id") Long id){
        return projetService.findProjetByCategory_Id(id);
    }

    @GetMapping("/userprojets/{id}")
    public ResponseEntity<?> getUserProjet(@PathVariable("id") Long id){
        return projetService.getUserProjets(id);
    }

    @GetMapping("/admin-accepted")
    public ResponseEntity<List<ResponseProjet>> getAllAdminAcceptedProjects(){
        return ResponseEntity.ok(projetService.getAllAdminAcceptedProjects());
    }

    @GetMapping("admin-pending")
    public ResponseEntity<List<ResponseProjet>> getAllAdminPendedProjects(){
        return ResponseEntity.ok(projetService.getAllAdminPendingProjects());
    }
    @GetMapping("admin-declined")
    public ResponseEntity<List<ResponseProjet>> getAllAdminDeclinedProjects(){
        return ResponseEntity.ok(projetService.getAllAdminDeclinedProjects());
    }

    @PutMapping("/admin-accept/{id}")
    public ResponseEntity<?> adminAcceptProjetct(@PathVariable("id") Long id){
        return projetService.adminAcceptProject(id);
    }
    @PutMapping("/admin-decline/{id}")
    public ResponseEntity<?> adminDeclineProjetct(@PathVariable("id") Long id){
        return projetService.adminDeclineProject(id);
    }

    @PutMapping("/title/{id}")
    public ResponseEntity<?> updateProjectTitle(@PathVariable("id") Long id , String title ){

        return projetService.updateProjetTitle(id,title);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePrject(@PathVariable("id") Long id, @RequestBody RequestProjet2 requestProjet2){
        return projetService.updateProject(id,requestProjet2);
    }
    @PutMapping("/description/{id}")
    public ResponseEntity<?> updateProjectDescription(@PathVariable("id") Long id , String desc ){

        return projetService.updateProjetTitle(id,desc);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<?> updateProjectCatgory(@PathVariable("id") Long id , Long catId ){

        return projetService.updateProjetCategory(id,catId);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProjet(@PathVariable("id") Long id){

         projetService.deleteProjet(id);
         return  ResponseEntity.noContent().build();
    }


    @GetMapping("/open")
    public ResponseEntity<?> getAllOpenProjects(){
     return projetService.getAllOpenProjet();
    }

    @GetMapping("/closed")
    public ResponseEntity<?> getAllClosedProjects(){
        return projetService.getAllClosedProjet();
    }

    @PutMapping("/openProjet/{id}")
    public ResponseEntity<?> openProjet(@PathVariable ("id") Long id){
        return projetService.openProjet(id);
    }

    @PutMapping("/closeProjet/{id}")
    public ResponseEntity<?> closeProjet(@PathVariable ("id") Long id){
        return projetService.closeProjet(id);
    }

    @GetMapping("/users/{id}/email")
    public ResponseEntity<String> getEmailById(@PathVariable Long id) {
        try {
            // Use the Feign client to fetch the email
            ResponseEntity<String> response = userClient.findEmailById(id);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            // Handle the exception as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching email");
        }
    }

















}
