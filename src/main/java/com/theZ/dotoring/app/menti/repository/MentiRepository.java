package com.theZ.dotoring.app.menti.repository;

import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.mento.model.Mento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface MentiRepository extends JpaRepository<Menti,Long> {

    @Query("SELECT M FROM Menti M JOIN FETCH M.profile WHERE M.mentiId = :mentiId")
    Optional<Menti> findMentiWithProfileUsingFetchJoinByMentiId(@Param("mentiId") Long mentiId);
}
