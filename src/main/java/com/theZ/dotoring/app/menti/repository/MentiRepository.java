package com.theZ.dotoring.app.menti.repository;

import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MentiRepository extends JpaRepository<Menti,Long> {

    @Query("SELECT M FROM Menti M JOIN FETCH M.profile WHERE M.mentiId = :mentiId")
    Optional<Menti> findMentiWithProfileUsingFetchJoinByMentiId(@Param("mentiId") Long mentiId);

    @Query("SELECT distinct M FROM Menti M JOIN FETCH M.profile JOIN FETCH M.memberMajors WHERE M.mentiId in :mentiIds and M.status = :status")
    List<Menti> findMentisWithProfileAndFieldsAndMajorsUsingFetchJoinByMentoId(@Param("mentiIds") List<Long> mentiIds, @Param("status") Status status);

    @Query("SELECT M FROM Menti M JOIN FETCH M.profile JOIN FETCH M.memberMajors WHERE M.mentiId = :mentiId")
    Optional<Menti> findMentiWithProfileAndMajorsUsingFetchJoinByMentoId(@Param("mentiId") Long mentiId);

    @Query("SELECT M FROM Menti M WHERE M.status = :status")
    Page<Menti> findMentisByStatus(@Param("status") Status status, Pageable pageable);

}
