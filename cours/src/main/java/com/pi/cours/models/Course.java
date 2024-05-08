package com.pi.cours.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer userId;
    private BigDecimal price;
    private Integer rate;
    // In your Course model
    private Double averageCompletionTime; // average time in hours to complete the course
    private Integer enrollmentCount; // number of enrolled students


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Video> videos = new ArrayList<>();
    public void addVideo(Video video) {
        this.videos.add(video);
        video.setCourse(this);
    }
    // Getters and setters
}
