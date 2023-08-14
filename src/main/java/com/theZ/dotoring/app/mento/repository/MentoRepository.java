package com.theZ.dotoring.app.mento.repository;

import com.theZ.dotoring.app.mento.model.Mento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MentoRepository extends JpaRepository<Mento,Long> {

    @Query("SELECT M FROM Mento M JOIN FETCH M.profile WHERE M.mentoId = :mentoId")
    public Optional<Mento> findMentoWithProfileUsingFetchJoinByMentoId(@Param("mentoId") Long mentoId);

}
