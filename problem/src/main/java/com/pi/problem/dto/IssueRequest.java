package com.pi.problem.dto;

import com.pi.problem.enums.Priority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueRequest  {
    private String issueTitle;
    private String issueDescription;
    @Lob
    private byte[] uriImage;
    @Enumerated(EnumType.STRING)
    private Priority priority;

}
