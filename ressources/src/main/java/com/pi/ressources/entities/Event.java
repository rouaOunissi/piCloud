package com.pi.ressources.entities;

import java.io.Serializable;

public class Event implements Serializable {
    private String date;
    private String title;

    // Constructeur
    public Event(String date, String title) {
        this.date = date;
        this.title = title;
    }
    // Getter pour la propriété "date"
    public String getDate() {
        return date;
    }

    // Getter pour la propriété "title"
    public String getTitle() {
        return title;
    }
}
