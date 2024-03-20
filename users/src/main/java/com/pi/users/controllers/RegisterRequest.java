package com.pi.users.controllers;

import com.pi.users.entities.Role;
import com.pi.users.entities.Speciality;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName ;
    private String lastName ;
    private String email ;
    private String password ;
    private int level ;
    private int numTel ;
    private Role role;
    private Speciality speciality ;

}
