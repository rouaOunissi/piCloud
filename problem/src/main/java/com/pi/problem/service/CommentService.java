package com.pi.problem.service;

import com.pi.problem.dto.CommentRequest;
import com.pi.problem.dto.CommentResponse;
import com.pi.problem.interfaces.IComment;
import com.pi.problem.model.Comment;
import com.pi.problem.model.Issue;
import com.pi.problem.model.React;
import com.pi.problem.repository.CommentDao;
import com.pi.problem.repository.ReactDao;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements IComment {

    @Resource
    private final CommentDao commentDao;
    private final IssueService service;
    private final ReactDao reactDao;

    @Override
    public void addCommentToIssue(int id, CommentRequest commentRequest) throws ParseException {
        Date currentDateTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = dateFormat.format(currentDateTime);
        Date parsedDate = dateFormat.parse(formattedDate);

        Issue issue = service.getTargetIssue(id);

        Comment newComment = Comment.builder()
                .id_user(commentRequest.getId_user())
                .comment_details(commentRequest.getComment_details())
                .nbr_reaction(0)
                .creation_date(parsedDate)
                .build();
        newComment.setIssue(issue);

        commentDao.save(newComment);


    }
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CommentResponse getCommentById(int id_comment) {
        Comment comment = commentDao.findById(id_comment).get();
        CommentResponse commentResponse = CommentResponse
                .builder()
                .id_comment(comment.getId_comment())
                .comment_details(comment.getComment_details())
                .creation_date(comment.getCreation_date())
                .nbr_reaction(comment.getNbr_reaction())
                .id_user(comment.getId_user())
                .build();
        return commentResponse;
    }
    @Override
    public List<CommentResponse> getCommentByIssue(int id_issue) {
        List<Comment> comments = commentDao.getCommentByIssue(id_issue);
        return comments.stream().map(this::mapTOCommentResponse).toList();

    }
    @Override
    public List<CommentResponse> getCommentByUser(long id_user) {
        List<Comment> comments = commentDao.getCommentByUser(id_user);
        return comments.stream().map(this::mapTOCommentResponse).toList();
    }
    @Override
    public void updateComment(int id_comment, CommentRequest commentRequest) {
        Comment comment = commentDao.findById(id_comment).get();
        comment.setComment_details(commentRequest.getComment_details());
        commentDao.save(comment);
    }
    public void deleteComment(int id_comment) {
        Comment comment = commentDao.findById(id_comment).orElse(null);
        if (comment != null) {
            comment.setIssue(null);
            commentDao.delete(comment);
        }
    }
    public void incrementComment(int id_comment){
        Comment comment = commentDao.findById(id_comment).get();
        int nbr_comment = comment.getNbr_reaction();
        nbr_comment= nbr_comment +1;
        comment.setNbr_reaction(nbr_comment);
        commentDao.save(comment);
    }
    public void decrementComment(int id_comment){
        Comment comment = commentDao.findById(id_comment).get();
        int nbr_comment = comment.getNbr_reaction();
        nbr_comment= nbr_comment -1;

        comment.setNbr_reaction(nbr_comment);
        commentDao.save(comment);
    }
    public Comment findOneComment(int id_comment){
        return commentDao.findById(id_comment).get();

    }

    public List<CommentResponse> getAllComment(){
        List<Comment> comments = commentDao.findAll();
        return comments.stream().map(this::mapTOCommentResponse).toList();

    }

    private CommentResponse mapTOCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id_user(comment.getId_user())
                .id_comment(comment.getId_comment())
                .comment_details(comment.getComment_details())
                .nbr_reaction(comment.getNbr_reaction())
                .creation_date(comment.getCreation_date())
                .build();
    }

    public Integer getNumberReactByComment(int idComment) {
        return commentDao.getReactionCountForComment(idComment);
    }


}
