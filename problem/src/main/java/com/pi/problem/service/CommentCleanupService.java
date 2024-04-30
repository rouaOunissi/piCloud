package com.pi.problem.service;


import com.pi.problem.model.Comment;
import com.pi.problem.repository.CommentDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class CommentCleanupService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private CommentService commentService;
    @Autowired
    private SendMailService sendMailService;
    @Scheduled(fixedRate = 30000)
    public void cleanupComments() {
        List<Comment> comments = commentDao.findAll();
        for (Comment comment : comments) {
            if (containsBadWord(comment.getComment_details())) {
                commentService.deleteComment(comment.getId_comment());
                System.out.println("Comment " + comment.getId_comment() + " deleted and mail should be sent ");
                this.sendEmail(comment.getId_user());
            }
        }
    }
    private boolean containsBadWord(String text) {
        return text.contains("****");
    }
    public void sendEmail(long id_user){

        String username ="Roua Ounissi";

        String to,body,title;
        to="ounissiroua1@gmail.com";
        title="Temporary Account Suspension Notice";

        body="Dear "+ username+ ",  We hope this message finds you well. We regret to inform you that your account has been temporarily suspended for a period of one day due to a violation of our community guidelines.\n" +
                "\n" +
                "We take the rules and regulations of our community seriously to ensure a safe and respectful environment for all users. Upon review of your recent actions, it was determined that they did not align with our guidelines.\n" +
                "\n" +
                "Your account will be reinstated after the suspension period has elapsed. We kindly ask that you review our community guidelines to avoid similar incidents in the future.\n" +
                "\n" +
                "If you have any questions or concerns regarding this suspension, please feel free to reach out to our support team at ";
        sendMailService.sendEmailMessage(to,title,body);

        this.banUser(id_user);
    }
    void banUser(long id_user){
        System.out.println("right now we gonna Ban ours User ");
        /*
        * In this code we gonne to Ban our user
        * User user = findUserByID(id_user)
        * user.setStatus(disable)
        * updateuser(user)*/

    }

}
