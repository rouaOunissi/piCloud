package com.pi.problem.repository;

import com.pi.problem.enums.Priority;
import com.pi.problem.enums.Status;
import com.pi.problem.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueDao extends JpaRepository<Issue,Integer> {
    @Query("SELECT i FROM Issue i WHERE i.priority = :priority ORDER BY i.creationDate DESC")
    public abstract List<Issue> findIssueByPriority(Priority priority);

    @Query("SELECT i FROM Issue i WHERE i.id_user = :idUser ORDER BY i.creationDate DESC")
    public abstract List<Issue> findIssueByUser(long idUser);

    @Query("SELECT i FROM Issue i WHERE i.status = :status ORDER BY i.creationDate DESC")
    public abstract List<Issue> findIssueByStatus(Status status);
}
