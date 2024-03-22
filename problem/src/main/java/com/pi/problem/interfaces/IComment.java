package com.pi.problem.interfaces;

import com.pi.problem.dto.CommentRequest;
import com.pi.problem.dto.CommentResponse;

import java.text.ParseException;
import java.util.List;
public interface IComment {
    void addCommentToIssue(int id, CommentRequest commentRequest) throws ParseException;
    CommentResponse getCommentById(int id_comment);
    List<CommentResponse>  getCommentByIssue(int id_issue);

    List<CommentResponse> getCommentByUser(long id_user);

    void updateComment (int id_comment , CommentRequest commentRequest);
    void deleteComment(int id_comment);





}
