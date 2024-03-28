package com.pi.users.controllers;

import com.pi.users.entities.User;
import com.pi.users.services.UserServices;
import com.pi.users.servicesImpl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService ;

    @Autowired
    private UserServices userService;



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        Optional<User> existingUser = authService.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
        }

        authService.register(request);
        return ResponseEntity.ok("Registration successful");
    }


   


    @PostMapping("/authentificate")
    public ResponseEntity<AuthentificationResponse> authentificate(@RequestBody AuthentificationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }





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
