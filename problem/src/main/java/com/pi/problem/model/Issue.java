package com.pi.problem.model;


import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="issue")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Issue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_issue;
    private long id_user;
    private String issueTitle;
    private String issueDescription;
    private String uriImage;
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;

}
