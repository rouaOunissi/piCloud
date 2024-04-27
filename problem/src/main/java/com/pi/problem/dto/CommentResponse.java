package com.pi.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private int id_comment;
    private long id_user;
    private String comment_details;
    private Date creation_date;
    private int nbr_reaction;
}
