package com.pi.problem.controller;


import com.pi.problem.service.ReactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/react")
@RequiredArgsConstructor
public class ReactController {


    @Autowired
    private final ReactService reactService;

    @GetMapping("/userReact/{id_comment}")
    public boolean FindReactOfUser(@PathVariable int id_comment){
        int id_user=1;
        return reactService.verifyUserReaction(id_user,id_comment);

    }
    @PostMapping("/comment/{id_comment}")
    public String setReaction(@PathVariable int id_comment) {
       return reactService.setReaction(id_comment);
    }


}
