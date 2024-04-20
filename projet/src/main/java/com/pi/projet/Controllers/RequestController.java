package com.pi.projet.Controllers;

import com.pi.projet.DTO.RequestRequest;
import com.pi.projet.DTO.ResponseProjet;
import com.pi.projet.Services.RequestService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projets/requests")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody RequestRequest requestRequest){
        return requestService.createRequest(requestRequest);
    }

    // requests li jou l projets mou3ayen

    //lazem nzidou hedhi fl front
    @GetMapping("/projet/{id}")
    public ResponseEntity<?> getRequestByProjectId(@PathVariable("id") Long id){
        return requestService.getRequestByProjetId(id);
    }



    //request li nta baaethhom l'encadreur lel les projets lkol

    //fl front fama hedhi
    @GetMapping("/myrequests/{id}")
    public ResponseEntity<?> getRequestByEncadreurId(@PathVariable ("id") Long id){
        return requestService.getRequestByUserId(id);
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptRequest(@PathVariable("id") Long reqId){
    return requestService.acceptRequest(reqId);
    }

    @PutMapping("/decline/{id}")
    public ResponseEntity<?> declineRequest(@PathVariable("id") Long reqId){
        return requestService.declineRequest(reqId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRequest(@PathVariable("id") Long id){
          requestService.deleteRequest(id);
    }





}
