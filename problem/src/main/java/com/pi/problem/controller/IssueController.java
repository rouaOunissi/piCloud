package com.pi.problem.controller;


import com.pi.problem.dto.IssueRequest;
import com.pi.problem.dto.IssueResponse;
import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import com.pi.problem.model.Issue;
import com.pi.problem.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/issue")
@RequiredArgsConstructor
@CrossOrigin("*")
public class IssueController {
    @Autowired
    private final IssueService issueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addIsssue(@RequestParam("title") String title, @RequestParam("description") String description,
                          @RequestParam("image") MultipartFile image, @RequestParam("priority") Priority priority,@RequestParam("id_user") long id_user ) throws ParseException, IOException {
        this.issueService.addIssueWithImg(title,description,image,priority,id_user);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IssueResponse getSingleIssue(@PathVariable int id){
        return this.issueService.getIssueByID(id);
    }
    @GetMapping("/priority/{priority}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponse> getIssueByPriority(@PathVariable Priority priority){
        return this.issueService.getIssueByPriority(priority);
    }
    @GetMapping("/user/{id_user}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponse> getIssueByUser(@PathVariable int id_user){
        return this.issueService.getIssueByUser(id_user);
    }
    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueResponse> getIssueByStatus(@PathVariable Status status){
        return this.issueService.getIssueByStatus(status);
    }
    @PutMapping("/update/{id_issue}")
    @ResponseStatus(HttpStatus.OK)
    public void updateIssue( @PathVariable int id_issue,
                             @RequestParam("id_user") long id_user,
                             @RequestParam("title") String title,
                             @RequestParam("description") String issueDescription,
                             @RequestParam("image") MultipartFile image,
                             @RequestParam("priority") Priority priority,
                             @RequestParam("status") Status status) throws IOException, ParseException {
        Date currentDateTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = dateFormat.format(currentDateTime);
        Date parsedDate = dateFormat.parse(formattedDate);
            Issue issue = Issue.builder()
                    .id_issue(id_issue)
                    .id_user(id_user)
                    .issueTitle(title)
                    .priority(priority)
                    .status(status)
                    .creationDate(parsedDate)
                    .uriImage(image.getBytes())
                    .issueDescription(issueDescription)
                    .build();
         this.issueService.updateIssue(issue);
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
    @PutMapping("update-status/{id_issue}")
    public void updateStatusIssue(@PathVariable int id_issue,@RequestParam("status") Status status){
        issueService.updateIssueStatus(id_issue,status);
    }





}
