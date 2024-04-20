package com.pi.users.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInterestDTO {
    private Long id;
    private String email;
    private List<String> interests;

    public UserInterestDTO(Long id, String email, List<String> interests) {
        this.id = id;
        this.email = email;
        this.interests = interests;
    }
}
