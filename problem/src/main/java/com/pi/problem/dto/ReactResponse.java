package com.pi.problem.dto;

import com.pi.problem.model.Comment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReactResponse {
    int id_react;
    long id_user;
    int id_comment;

}
