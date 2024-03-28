package com.pi.users.servicesImpl;

import com.pi.users.controllers.AuthentificationRequest;
import com.pi.users.controllers.AuthentificationResponse;
import com.pi.users.controllers.RegisterRequest;
import com.pi.users.entities.Role;
import com.pi.users.entities.User;
import com.pi.users.jwt.JwtService;
import com.pi.users.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepo userRepo ;
    private final PasswordEncoder passwordEncoder ;
    private final JwtService jwtService ;
    private final AuthenticationManager authenticationManager ;
    public ResponseEntity<?> register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))

                .level(request.getLevel())
                .numTel(request.getNumTel())
                .speciality(request.getSpeciality())
                .build();
        user.setRole(Role.STUDENT);

        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");

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
