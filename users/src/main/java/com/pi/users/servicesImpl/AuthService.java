package com.pi.users.servicesImpl;

import com.pi.users.controllers.AuthentificationRequest;
import com.pi.users.controllers.AuthentificationResponse;
import com.pi.users.controllers.RegisterRequest;
import com.pi.users.entities.User;
import com.pi.users.jwt.JwtService;
import com.pi.users.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
                .role(request.getRole())
                .level(request.getLevel())
                .numTel(request.getNumTel())
                .speciality(request.getSpeciality())
                .build();

        userRepo.save(user);

        return ResponseEntity.ok("User registered successfully");

    }

    public AuthentificationResponse authentificate(AuthentificationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken= jwtService.generateToken(user);

        return AuthentificationResponse.builder().token(jwtToken).build();


    }
}
