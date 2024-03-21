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
    public ResponseEntity<?> createProject(@RequestBody RequestProjet project, @RequestHeader("Authorization") String bearerToken) {
        ResponseProjet createdProject = projetService.createProject(project, bearerToken);
        if (createdProject != null)
            return ResponseEntity.ok(createdProject);
        else {
            String s = "Cannot create project";
            return ResponseEntity.badRequest().body(s);
        }
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<List<ResponseProjet>> getProjectByCategory(@PathVariable("category_id") Long id){
        return projetService.findProjetByCategory_Id(id);
    }







}
