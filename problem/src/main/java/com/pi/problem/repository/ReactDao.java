package com.pi.problem.repository;

import com.pi.problem.dto.ReactResponse;
import com.pi.problem.model.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.ui.Model;

import java.util.List;
public interface ReactDao extends JpaRepository<React,Integer> {

    @Query("SELECT r FROM React r WHERE r.id_user = :id_user AND r.comment.id_comment = :id_comment")
    React findReactByUserAndComment(long id_user, int id_comment);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM React r WHERE r.id_user = :id_user and r.comment.id_comment = :id_comment ")
    boolean FindReactOfUser(long id_user, int id_comment);

    @Query("DELETE FROM React r WHERE r.id_user = :idUser AND r.comment.id_comment = :idComment")
    void deleteReactByUserAndComment(long idUser, int idComment);
    @Query("select r FROM React r WHERE r.id_user = :idUser ")
    List<React> findByIdUser(long idUser);
    @Query("select r FROM React r WHERE r.comment.id_comment = :id_comment ")
    List<React> findByCommentID(int id_comment);

  /*  @Query(" SELECT COUNT(r) FROM React r WHERE r.comment.id_comment = :commentId")
    List<React> getNumberofReactByCommentID(int id_comment);
*/
}
