package com.pi.problem.service;


import com.pi.problem.interfaces.IReact;
import com.pi.problem.model.Comment;
import com.pi.problem.model.React;
import com.pi.problem.repository.ReactDao;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactService implements IReact {

    @Resource
    private final ReactDao reactDao;
    @Autowired
    private final CommentService commentService;



    public boolean verifyUserReaction(int id_user,int id_comment){
       return   reactDao.FindReactOfUser(id_user,id_comment);
    }
    public String setReaction(int id_comment){
        int id_user = 1;
        Comment comment = commentService.findOneComment(id_comment);


        if(!verifyUserReaction(id_user,id_comment)){
            //add react
            React react = React.builder()
                    .id_user(id_user)
                    .comment(comment)
                    .build();
            reactDao.save(react);
            commentService.incrementComment(id_comment);
            return "Reaction Added";
            //increment Comment.nbrreact

        }else {
            //deletereact
            React react = reactDao.findReactByUserAndComment(id_user,id_comment);
            if(react != null){
                react.setComment(null);
                reactDao.delete(react);
                commentService.decrementComment(id_comment);


            }
            return "Reaction removed";


        }



    }

}
