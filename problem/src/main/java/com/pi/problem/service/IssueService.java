package com.pi.problem.service;

import com.pi.problem.dto.IssueRequest;
import com.pi.problem.dto.IssueResponse;
import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import com.pi.problem.interfaces.IIssue;
import com.pi.problem.model.Comment;
import com.pi.problem.model.Issue;
import com.pi.problem.repository.CommentDao;
import com.pi.problem.repository.IssueDao;
import com.pi.problem.utils.ImageUtils;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService implements IIssue {

    @Resource
    private final IssueDao issueDao;
    private final CommentDao commentDao;
    @PersistenceContext
    private EntityManager entityManager;
    @Transactional
    public void deleteIssue(int issueId) {
        Issue issue = issueDao.findById(issueId).orElse(null);
        if (issue != null) {
            // Delete associated comments first
            List<Comment> comments = commentDao.getCommentByIssue(issueId);
            for (Comment comment : comments) {
                entityManager.remove(comment);
            }
            entityManager.flush();
            entityManager.clear();

            // Then delete the issue
            issueDao.delete(issue);
        }
    }


    @Override
    public void addIssueWithImg(String title, String description, MultipartFile image, Priority priority,long id_user) throws ParseException, IOException {
        Date currentDateTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = dateFormat.format(currentDateTime);
        Date parsedDate = dateFormat.parse(formattedDate);

        Issue issue = Issue.builder()
                .issueTitle(title)
                .issueDescription(description)
                .priority(priority)
                .uriImage((image.getBytes()))
                .creationDate(parsedDate)
                .status(Status.OPEN)
                .id_user(id_user)
                .build();
        issueDao.save(issue);

    }

    @Override
    public IssueResponse getIssueByID(int id) {
        Issue issue = issueDao.findById(id).get();
        IssueResponse issueResponse = IssueResponse
                .builder()
                .id_issue(issue.getId_issue())
                .id_user(issue.getId_user())
                .issueTitle(issue.getIssueTitle())
                .issueDescription(issue.getIssueDescription())
                .uriImage(issue.getUriImage())
                .creationDate(issue.getCreationDate())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .build();
        return issueResponse;
    }

    @Override
    public Issue getTargetIssue(int id) {
        Issue issue = issueDao.findById(id).get();
        return issue;
    }

    @Override
    public List<IssueResponse> getIssueByUser(long id_user) {
        List<Issue> issues = issueDao.findIssueByUser(id_user);
        return issues.stream().map(this::mapToIssueResponse).toList();
    }

    @Override
    public List<IssueResponse> getIssueByPriority(Priority priority) {
        List<Issue> issues = issueDao.findIssueByPriority(priority);
        return issues.stream().map(this::mapToIssueResponse).toList();
    }

    @Override
    public List<IssueResponse> getIssueByStatus(Status status) {
        List<Issue> issues = issueDao.findIssueByStatus(status);
        return issues.stream().map(this::mapToIssueResponse).toList();
    }

    @Override
    public void updateIssue( Issue issue) {
        issueDao.save(issue);


    }

    @Override
    public List<IssueResponse> getAllIssue() {
        List<Issue> issues = issueDao.findAll();

        return issues.stream().map(this::mapToIssueResponse).toList();
    }


    private IssueResponse mapToIssueResponse(Issue issue) {
        return IssueResponse.builder()
                .id_issue(issue.getId_issue())
                .id_user(issue.getId_user())
                .issueTitle(issue.getIssueTitle())
                .issueDescription(issue.getIssueDescription())
                .uriImage(issue.getUriImage())
                .creationDate(issue.getCreationDate())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .build();
    }

}