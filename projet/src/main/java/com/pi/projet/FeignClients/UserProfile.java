package com.pi.projet.FeignClients;

public class UserProfile {
    private Long id;

    public UserProfile(Long id) {
        this.id = id;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
