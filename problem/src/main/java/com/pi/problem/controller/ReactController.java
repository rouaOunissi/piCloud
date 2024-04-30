package com.pi.problem.controller;


import com.netflix.discovery.converters.Auto;
import com.pi.problem.dto.IssueResponse;
import com.pi.problem.dto.ReactResponse;
import com.pi.problem.model.React;
import com.pi.problem.repository.ReactDao;
import com.pi.problem.service.ReactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/react")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReactController {

    @Autowired
    private final ReactService service;

    @GetMapping("/userReact/{id_comment}")
    public boolean FindReactOfUser(@PathVariable int id_comment,@RequestParam("id_user") long id_user){
        return service.verifyUserReaction(id_user,id_comment);

    }
    @PostMapping("/comment/{id_comment}")
    public String setReaction(@PathVariable int id_comment,@RequestParam("id_user") long id_user) {
        return service.setReaction(id_comment,id_user);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReactResponse getReactByID(@PathVariable int id){

        return this.service.getReactByID(id);
    }

    @GetMapping("/user/{id_user}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReactResponse> getReactByIDUser(@PathVariable int id_user){
        return this.service.getReactByIDUser(id_user);
    }
    @GetMapping("/comment/{id_comment}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReactResponse> getReactBycommentID(@PathVariable int id_comment){
        return this.service.getReactByCommentID(id_comment);
    }

    /*
    @GetMapping("/number/comment/{id_comment}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReactResponse> getnomberofReactBycommentID(@PathVariable int id_comment){
        return this.service.getnumberReactByCommentID(id_comment);
    }
*/




}
