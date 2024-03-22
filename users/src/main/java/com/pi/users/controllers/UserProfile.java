package com.pi.users.controllers;

public class UserProfile {
    private Long id;
    public UserProfile(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
