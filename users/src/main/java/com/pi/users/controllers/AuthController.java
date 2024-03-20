package com.pi.users.controllers;

import com.pi.users.entities.User;
import com.pi.users.servicesImpl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService ;

   /* @PostMapping("/register")
    public ResponseEntity<AuthentificationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }*/
   @PostMapping("/register")
   public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
       authService.register(request);
       return ResponseEntity.ok("Registration successful");

   }


    @PostMapping("/authentificate")
    public ResponseEntity<AuthentificationResponse> authentificate(@RequestBody AuthentificationRequest request){
        return ResponseEntity.ok(authService.authentificate(request));
    }

    @GetMapping("/mon-profil")
    public ResponseEntity<?> monProfil() {

       // récupèrer l'objet Authentication qui contient des informations sur l'utilisateur authentifié.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
           // extraire l'utilisateur authentifié de l'objet Authentication
            User user = (User) authentication.getPrincipal();
            Long userId = user.getIdUser();

            return ResponseEntity.ok().body(userId);
        } else {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilisateur non authentifié");
        }
    }

}
