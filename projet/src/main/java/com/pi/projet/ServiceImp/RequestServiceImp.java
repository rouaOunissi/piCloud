package com.pi.projet.ServiceImp;

import com.pi.projet.DTO.*;
import com.pi.projet.Services.ProjetService;
import com.pi.projet.Services.RequestService;
import com.pi.projet.email.EmailService;
import com.pi.projet.entities.ProjectStatus;
import com.pi.projet.entities.Projet;
import com.pi.projet.entities.Request;
import com.pi.projet.entities.RequestStatus;
import com.pi.projet.feign.UserClient;
import com.pi.projet.repositories.ProjetRepo;
import com.pi.projet.repositories.RequestRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private UserClient userClient;
    @Autowired
    private EmailService emailService;

    @Autowired
    WebSocketService webSocketService ;



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

            // Récupérer l'e-mail de l'encadreur
            Request requestt = request.get();
            Long encadreurId = requestt.getEncadreurId();
            ResponseEntity<String> responseEntity = userClient.findEmailById(encadreurId);

            // Récupérer l'e-mail de créateur
            Long idCreator = requestt.getProject().getCreatorId();
            ResponseEntity<String> CreatorEmail = userClient.findEmailById(idCreator);
            String creator = CreatorEmail.getBody().toString();
            if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {



                String email = responseEntity.getBody();
                String subject = "Demande Acceptée";
                String content = "Votre demande pour le projet " + projet.getTitle() + " a été acceptée."  + " veuillez contacter "+ creator;
                emailService.sendEmail(email, subject, content);


                // Optionally, send a notification to the encadreur's WebSocket session if they are online
                // This would require having a WebSocketService as shown in the previous messages
                webSocketService.sendNotification(encadreurId.toString(), "Votre demande pour le projet " + projet.getTitle() + " a été acceptée.");



                return ResponseEntity.ok("Request accepted and the encadreur has been notified!");
            } else {
                // Handle the case where the email could not be retrieved
                return ResponseEntity.status(responseEntity.getStatusCode()).body("Could not retrieve email");
            }

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
