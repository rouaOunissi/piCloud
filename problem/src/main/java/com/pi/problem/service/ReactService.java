package com.pi.problem.service;

import com.pi.problem.dto.ReactResponse;
import com.pi.problem.interfaces.IReact;
import com.pi.problem.model.Comment;
import com.pi.problem.model.React;
import com.pi.problem.repository.ReactDao;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactService implements IReact {

    @Resource
    private final ReactDao dao;
    private final CommentService commentService;
    @Override
    public boolean verifyUserReaction(long id_user, int id_comment) {
        return dao.FindReactOfUser(id_user,id_comment);    }

    @Override
    public String setReaction(int id_comment ,long id_user ) {
        Comment comment = commentService.findOneComment(id_comment);


        if(!verifyUserReaction(id_user,id_comment)){
            //add react
            React react = React.builder()
                    .id_user(id_user)
                    .comment(comment)
                    .build();
            dao.save(react);
            commentService.incrementComment(id_comment);
            return "Reaction Added";
            //increment Comment.nbrreact

        }else {
            //deletereact
            React react = dao.findReactByUserAndComment(id_user,id_comment);
            if(react != null){
                react.setComment(null);
                dao.delete(react);
                commentService.decrementComment(id_comment);


            }
            return "Reaction removed";
        }
    }

    public ReactResponse getReactByID(int id) {
        React react =dao.findById(id).get();
        return ReactResponse.builder()
                .id_react(react.getId_react())
                .id_user(react.getId_user())
                .id_comment(react.getComment().getId_comment()).build();
    }

    public List<ReactResponse> getReactByIDUser(long idUser) {
        List<React> react =  dao.findByIdUser(idUser);
        return react.stream().map(this::mapTOReactResponse).toList();

    }
    public List<ReactResponse> getReactByCommentID(int id_comment) {
         List<React> react=  dao.findByCommentID(id_comment);
       return react.stream().map(this::mapTOReactResponse).toList();

    }

    private ReactResponse mapTOReactResponse(React react) {
        return ReactResponse.builder()
                .id_comment(react.getComment().getId_comment())
                .id_user(react.getId_user())
                .id_react(react.getId_react())
                .build();
    }
  /*  public List<ReactResponse> getnumberReactByCommentID(int commentId) {
        List<React> react=  dao.getNumberofReactByCommentID(commentId);
        return react.stream().map(this::mapTOReactResponse).toList();
    }
*/

}
