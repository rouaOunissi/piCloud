package com.pi.users.repository;

import com.pi.users.entities.Role;
import com.pi.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);


    User findByRole(Role role);

    List<User> findByFirstNameContaining(String firstName);
    Optional<User> findByConfirmationToken(String confirmationToken);

    @Query("SELECT u.email FROM User u WHERE u.idUser = :id")
    Optional<String> findEmailById(@Param("id") Long id);

    @Query("SELECT FUNCTION('MONTH', u.registrationDate) AS month, COUNT(u) AS count FROM User u GROUP BY FUNCTION('MONTH', u.registrationDate)")
    List<Object[]> countUsersByRegistrationMonth();


}
