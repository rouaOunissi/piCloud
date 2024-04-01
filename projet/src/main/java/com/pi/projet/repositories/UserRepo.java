package com.pi.projet.repositories;

import com.pi.projet.entities.UserCridentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserCridentials,Long> {
}
