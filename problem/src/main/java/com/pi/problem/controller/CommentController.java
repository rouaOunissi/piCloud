package com.pi.problem.controller;

import com.pi.problem.dto.CommentRequest;
import com.pi.problem.dto.CommentResponse;
import com.pi.problem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private final CommentService service;

    @PostMapping("/issueID/{id_issue}")
    @ResponseStatus(HttpStatus.CREATED)
    public void  addCommentToPost(@PathVariable int id_issue, @RequestBody CommentRequest commentRequest) throws ParseException {
         service.addCommentToIssue(id_issue,commentRequest);
    }

    @GetMapping("/{id_comment}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse getCommentByID(@PathVariable int id_comment){
        return service.getCommentById(id_comment);
    }
    @GetMapping("/issue")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getCommentByIssue(@RequestParam int id_issue){
        return service.getCommentByIssue(id_issue);
    }
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponse> getCommentByUser(@RequestParam long id_user) {
        return service.getCommentByUser(id_user);

    }
    @PutMapping("/update/{id_comment}")
    @ResponseStatus(HttpStatus.OK)
    public void updateComment(@PathVariable int id_comment,@RequestBody CommentRequest commentRequest){
        service.updateComment(id_comment, commentRequest);
    }

    @DeleteMapping("/delete/{id_comment}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable int id_comment ){
        this.service.deleteComment(id_comment);
    }


}
