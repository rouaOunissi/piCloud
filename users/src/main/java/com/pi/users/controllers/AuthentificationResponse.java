package com.pi.users.controllers;

import com.pi.users.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthentificationResponse {
    private String token ;
    private Long idUser ;
    private Role role ;
}
