package com.pi.problem.dto;

import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponse   {
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
