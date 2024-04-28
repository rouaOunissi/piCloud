package com.pi.ressources.Dao;

import com.pi.ressources.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReactionDao extends JpaRepository<Reaction,Long> {

    boolean existsReactionByIdReactionAndAndIdUser(Long resourceId, Long userId);

    @Query(value = "SELECT * FROM reaction WHERE ressource_id = :ressourceId AND id_user = :userId", nativeQuery = true)
    Reaction findReactionByIdReactionAndIdUser(Long ressourceId, Long userId);

    @Query(value = "SELECT * FROM reaction WHERE ressource_id = :ressourceId AND id_user = :userId", nativeQuery = true)
    Optional<Reaction> findReactionByIdReactionAndIdUser2(Long ressourceId, Long userId);




}
