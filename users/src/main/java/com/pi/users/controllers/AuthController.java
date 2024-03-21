package com.pi.users.controllers;

import com.pi.users.entities.User;
import com.pi.users.jwt.JwtService;
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


 /*   @GetMapping("/mon-profil")
    public ResponseEntity<UserProfile> monProfil() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();
            Long userId = user.getIdUser();
            UserProfile profile = new UserProfile(userId);

            return ResponseEntity.ok(profile);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }*/

    @GetMapping("/mon-profil")
    public ResponseEntity<?> monProfil() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User) {
                User user = (User) principal;
                Long userId = user.getIdUser();
                UserProfile profile = new UserProfile(userId);
                return ResponseEntity.ok(profile);
            } else {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur : L'objet principal de l'authentification n'est pas une instance de User.");
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Accès refusé : Vous devez être connecté pour accéder à cette ressource.");
    }





}
