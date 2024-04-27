package com.pi.problem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "react")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class React implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    int id_react;
    int id_user;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comment")
     Comment comment;
}
