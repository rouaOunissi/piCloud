package com.pi.users.controllers;

import com.pi.users.Dto.PasswordDto;
import com.pi.users.entities.Speciality;
import com.pi.users.entities.User;
import com.pi.users.repository.UserRepo;
import com.pi.users.services.UserServices;
import com.pi.users.servicesImpl.AuthService;
import jakarta.mail.MessagingException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService ;

    @Autowired
    private UserServices userService;
    @Autowired
    private UserRepo userRepo ;



    @PostMapping("/authentificate")
    public ResponseEntity<AuthentificationResponse> authentificate(@RequestBody AuthentificationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
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
        Optional<User> existingUser = authService.findByEmail(email);
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setLevel(level);
        newUser.setNumTel(numTel);
        newUser.setSpeciality(speciality);

        if (image != null && !image.isEmpty()) {
            newUser.setImage(image.getBytes());
        }

        authService.register(email, firstName, lastName, password, level, numTel, speciality, image);

        return ResponseEntity.ok(new ApiResponse("Registration successful"));
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

    private final Path rootLocation = Paths.get("src/main/resources/static/images");
    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token) {
        Optional<User> userOptional = userRepo.findByConfirmationToken(token);

        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        User user = userOptional.get();
        User u = userService.getUserById(user.getIdUser());

        if (!u.isEnabled()) {

            user.setEnabled(true);
            user.setConfirmationToken(null);
            userRepo.save(user);

            return ResponseEntity.ok("Account confirmed successfully!");
        } else {

            return ResponseEntity.badRequest().body("Account is already confirmed or token is no longer valid.");
        }



    }


    @GetMapping("/findEmail/{id}")
    public ResponseEntity<String> findEmailById(@PathVariable Long id) {
        return userService.findEmailById(id)
                .map(email -> ResponseEntity.ok().body(email))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/check-enabled")
    public ResponseEntity<?> isUserEnabled(@RequestParam String email) {
        Boolean isEnabled = userService.checkUserEnabled(email);
        if (isEnabled == null) {
            return ResponseEntity.badRequest().body("User with email " + email + " not found.");
        }
        return ResponseEntity.ok(isEnabled);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgitPassword(@RequestParam String email) throws MessagingException {
        return new ResponseEntity<>(userService.forgotPassword(email),HttpStatus.OK) ;
    }

    @PutMapping("/set-password")
    public ResponseEntity<Map<String, String>> setPassword(@RequestParam String email, @RequestBody PasswordDto passwordDto){
        String message = userService.setPassword(email, passwordDto.getNewPassword());
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("updateStatus/{id_user}")
    public void setUserStatus(@PathVariable("id_user") long id_user ){
        //Optional<User> userOptional = userRepo.findByConfirmationToken(token);

        //User user = userOptional.get();
        User u = userService.getUserById(id_user);

        if (u.isEnabled()) {

            u.setEnabled(false);
            u.setConfirmationToken(null);
            userRepo.save(u);

        }

      //  userService.setUserStatus(id_user);
    }














}
