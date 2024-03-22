package com.pi.projet.Controllers;

import com.pi.projet.DTO.RequestProjet;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.Services.ProjetService;
import com.pi.projet.entities.Projet;
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
public class ProjetController {

    private final ProjetService projetService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody RequestProjet project) {
        return  projetService.createProject(project);
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<ResponseProjet>> getProjectByCategory(@PathVariable("category_id") Long id){
        return projetService.findProjetByCategory_Id(id);
    }

    @GetMapping("/userprojets/{id}")
    public ResponseEntity<?> getUserProjet(@PathVariable("id") Long id){
        return projetService.getUserProjets(id);
    }

    @GetMapping
    public ResponseEntity<List<ResponseProjet>> getAllProjects(){
        return ResponseEntity.ok(projetService.getAllProjects());
    }

    @PutMapping("/title/{id}")
    public ResponseEntity<?> updateProjectTitle(@PathVariable("id") Long id , String title ){

        return projetService.updateProjetTitle(id,title);
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
        return projetService.deleteProjet(id);
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
















}
