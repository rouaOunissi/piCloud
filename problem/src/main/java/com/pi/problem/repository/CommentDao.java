package com.pi.problem.repository;

import com.pi.problem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Integer> {

    @Query("SELECT c FROM Comment c WHERE c.issue.id_issue = :idIssue ORDER BY c.creation_date DESC")
    List<Comment> getCommentByIssue(int idIssue);

    @Query("SELECT c FROM Comment c WHERE c.id_user = :idUser ORDER BY c.creation_date DESC")
    List<Comment> getCommentByUser(long idUser);

    @Query("SELECT c.nbr_reaction FROM Comment c WHERE c.id_comment = :commentId")
    Integer getReactionCountForComment(int commentId);


}
