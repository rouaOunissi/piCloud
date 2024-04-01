package com.pi.users.servicesImpl;

import com.pi.users.controllers.AuthentificationRequest;
import com.pi.users.controllers.AuthentificationResponse;
import com.pi.users.controllers.RegisterRequest;
import com.pi.users.entities.Role;
import com.pi.users.entities.Speciality;
import com.pi.users.entities.User;
import com.pi.users.jwt.JwtService;
import com.pi.users.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepo userRepo ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtService jwtService ;
    private final AuthenticationManager authenticationManager ;
    public ResponseEntity<?> register(
            @RequestParam("email") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("password") String password,
            @RequestParam("level") int level,
            @RequestParam("numTel") int numTel,
            @RequestParam("speciality") Speciality speciality,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        if (emailAlreadyExists(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password)) // Make sure to encode the password
                .level(level)
                .numTel(numTel)
                .speciality(speciality)
                .role(Role.STUDENT) // Assign a role if needed
                .build();

        if (image != null && !image.isEmpty()) {

                byte[] imageBytes = image.getBytes();
                user.setImage(imageBytes); // Set the image as a byte array

        }

        userRepo.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    private boolean emailAlreadyExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    public AuthentificationResponse authenticate (AuthentificationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Récupérer l'utilisateur depuis la base de données en utilisant l'e-mail
            User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

            // Générer le token JWT
            String jwtToken = jwtService.generateToken(user);

            // Créer une réponse contenant le token, l'ID de l'utilisateur et son rôle
            return AuthentificationResponse.builder()
                    .token(jwtToken)
                    .idUser(user.getIdUser())
                    .role(user.getRole())
                    .build();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username/password supplied", e);
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
