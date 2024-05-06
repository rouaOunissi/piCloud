package com.pi.cours.dao;


import com.pi.cours.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {
    Optional<Course> findByTitle(String title);

}

