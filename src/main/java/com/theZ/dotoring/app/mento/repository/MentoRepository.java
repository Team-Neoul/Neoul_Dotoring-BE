package com.theZ.dotoring.app.mento.repository;

import com.theZ.dotoring.app.mento.model.Mento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MentoRepository extends JpaRepository<Mento,Long> {

    @Query("SELECT M FROM Mento M JOIN FETCH M.profile WHERE M.mentoId = :mentoId")
    Optional<Mento> findMentoWithProfileUsingFetchJoinByMentoId(@Param("mentoId") Long mentoId);

    @Query("SELECT distinct M FROM Mento M JOIN FETCH M.profile JOIN FETCH M.memberMajors WHERE M.mentoId in :mentoIds")
    List<Mento> findMentosWithProfileAndFieldsAndMajorsUsingFetchJoinByMentoId(@Param("mentoIds") List<Long> mentoIds);

}
