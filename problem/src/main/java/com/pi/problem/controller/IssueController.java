package com.pi.problem.controller;


import com.pi.problem.dto.IssueRequest;
import com.pi.problem.dto.IssueResponse;
import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import com.pi.problem.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
public class IssueController {
    @Autowired
    private final IssueService issueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addIsssue(@RequestParam("title") String title, @RequestParam("descrtiption") String description,
                          @RequestParam("image") MultipartFile image, @RequestParam("priority") Priority priority) throws ParseException, IOException {
        this.issueService.addIssueWithImg(title,description,image,priority);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IssueResponse getSingleIssue(@PathVariable int id){
        return this.issueService.getIssueByID(id);
    }
    @GetMapping("/priority")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponse> getIssueByPriority(@RequestParam Priority priority){
        return this.issueService.getIssueByPriority(priority);
    }
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponse> getIssueByUser(@RequestParam int id_user){
        return this.issueService.getIssueByUser(id_user);
    }
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponse> getIssueByStatus(@RequestParam Status status){
        return this.issueService.getIssueByStatus(status);
    }
    @PutMapping("/update/{id_issue}")
    @ResponseStatus(HttpStatus.OK)
    public void updateIssue(@PathVariable int id_issue,@RequestBody IssueResponse issueResponse){
         this.issueService.updateIssue(id_issue,issueResponse);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponse> getAllIssue(){
        return this.issueService.getAllIssue();
    }

    @DeleteMapping("/delete/{id_issue}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteIssue(@PathVariable int id_issue ){
        this.issueService.deleteIssue(id_issue);
    }






}
