package com.pi.projet.ServiceImp;

import com.pi.projet.DTO.*;
import com.pi.projet.Services.ProjetService;
import com.pi.projet.Services.RequestService;
import com.pi.projet.entities.ProjectStatus;
import com.pi.projet.entities.Projet;
import com.pi.projet.entities.Request;
import com.pi.projet.entities.RequestStatus;
import com.pi.projet.repositories.ProjetRepo;
import com.pi.projet.repositories.RequestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImp implements RequestService {

    private final RequestRepo requestRepo;
    private final ProjetRepo projetRepo;
    private final ProjetService projetService;


    @Override
    public ResponseEntity<?> createRequest( RequestRequest requestRequest) {
       Optional <Projet> projet = projetRepo.findById(requestRequest.getProjectId());
       if(projet.isPresent() && requestRequest.getEncadreurId()!=null && requestRequest.getMessage()!=null && requestRequest.getProjectId()!=null){
           Projet  projet1=projet.get();
           Request request = this.mapDTOtoModel(requestRequest);
           request.setProject(projet1);
           requestRepo.save(request);
           return ResponseEntity.ok().body(new MessageResponse("Applied successfully "));
       }
       else
           return ResponseEntity.badRequest().body("Bad Request !");
    }

    @Override
    public ResponseEntity<?> getRequestByProjetId(Long id) {
        Optional<Projet> projet = projetRepo.findById(id);
        if(projet.isPresent()){
            Projet projet1 = projet.get();
            List<ResponseRequest> responseRequests=requestRepo.getRequestsByProject(projet1).stream().map(this::mapModeltoDTO).toList();

            return ResponseEntity.ok(responseRequests);
        }
        else
            return ResponseEntity.badRequest().body("Project Do Not Exist");
    }

    @Override
    public ResponseEntity<?> getRequestByUserId(Long id) {
    List<ResponseRequest2> responseRequest2s = requestRepo.getRequestsByEncadreurId(id);

    if (responseRequest2s.isEmpty())
        return ResponseEntity.ok("No requests yet !");
    else {
        return ResponseEntity.ok(responseRequest2s);
    }

    }

    @Override
    public ResponseEntity<?> acceptRequest(Long id) {
        Optional<Request> request=requestRepo.findById(id);
        if(request.isPresent()){
            Request request1 =request.get();
            request1.setStatus(RequestStatus.ACCEPTED);
            requestRepo.save(request1);
            Projet projet= request1.getProject();
            projet.setStatus(ProjectStatus.CLOSED);
            projetRepo.save(projet);
            return ResponseEntity.ok("Request Accepted ! ");

        }
        else
            return ResponseEntity.badRequest().body("Bad Request !");

    }

    @Override
    public ResponseEntity<?> declineRequest(Long id) {
        Optional<Request> request = requestRepo.findById(id);
        if(request.isPresent()){
            Request request1=request.get();
            request1.setStatus(RequestStatus.DECLINED);
            requestRepo.save(request1);
            return ResponseEntity.ok("Request Declined ! ");
        }
        return ResponseEntity.badRequest().body("Request Not Found!");
    }

    @Override
    public ResponseEntity<?> deleteRequest(Long id) {
        Optional<Request> request=requestRepo.findById(id);
        if (request.isPresent()){
            Request request1=request.get();
            requestRepo.delete(request1);
            return ResponseEntity.ok("Request Deleted ");
        }
        else
            return ResponseEntity.badRequest().body("Request Not Found");
    }


    public Request mapDTOtoModel(RequestRequest requestRequest){
        return  Request.builder()
                .encadreurId(requestRequest.getEncadreurId())
                .message(requestRequest.getMessage())
                .status(RequestStatus.PENDING)
                .build();
    }

    public ResponseRequest mapModeltoDTO (Request request){
        return ResponseRequest.builder()
                .encadreurId(request.getEncadreurId())
                .id(request.getId())
                .message(request.getMessage())
                .status(request.getStatus())
                .build();
    }

}
