package com.pi.cours.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer userId;
   // To store the type of file (video, PDF, image, etc.)
    @Lob
    @Column(length = 100000000,nullable = true)
    private byte[] fileContent; // To store the content of the file (Base64 encoded string, for example)
    private BigDecimal price ;

}
