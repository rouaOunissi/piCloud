package com.pi.cours.dao;

import com.pi.cours.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDao extends JpaRepository<Video, Long> {
}
