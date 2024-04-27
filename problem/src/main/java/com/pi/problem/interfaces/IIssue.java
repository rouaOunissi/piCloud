package com.pi.problem.interfaces;


import com.pi.problem.dto.IssueRequest;
import com.pi.problem.dto.IssueResponse;
import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import com.pi.problem.model.Issue;

import java.text.ParseException;
import java.util.List;
public interface IIssue {
    void addIssue(IssueRequest issueRequest) throws ParseException;
    IssueResponse getIssueByID(int id);
    Issue getTargetIssue(int id);
    List<IssueResponse> getIssueByUser(long id_user);

    List<IssueResponse> getIssueByPriority(Priority priority);

    List<IssueResponse> getIssueByStatus(Status status );

    void updateIssue(int id,IssueResponse issueResponse);
    List<IssueResponse> getAllIssue();
    void deleteIssue(int id_issue);


}
