package com.pi.cours.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String contenu;
    private Integer reactNumber;
    private String filePath;
    private Integer userId;
    private String fileType; // To store the type of file (video, PDF, image, etc.)
    private String fileContent; // To store the content of the file (Base64 encoded string, for example)

}
