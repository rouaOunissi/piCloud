package com.pi.problem.interfaces;


import com.pi.problem.dto.IssueRequest;
import com.pi.problem.dto.IssueResponse;
import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import com.pi.problem.model.Issue;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
public interface IIssue {
    void addIssueWithImg(String title, String description, MultipartFile image,Priority priority,long id_user) throws ParseException, IOException;
    IssueResponse getIssueByID(int id);
    Issue getTargetIssue(int id);
    List<IssueResponse> getIssueByUser(long id_user);

    List<IssueResponse> getIssueByPriority(Priority priority);

    List<IssueResponse> getIssueByStatus(Status status );

    void updateIssue(Issue issue);
    List<IssueResponse> getAllIssue();


}
