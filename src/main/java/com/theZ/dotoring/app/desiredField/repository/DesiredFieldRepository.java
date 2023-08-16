package com.theZ.dotoring.app.desiredField.repository;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DesiredFieldRepository extends JpaRepository<DesiredField,Long> {


    @Query("SELECT df FROM DesiredField df where df.menti.mentiId = :mentiId")
    List<DesiredField> findByMentiId(@Param("mentiId") Long mentiId);

    @Query("SELECT df FROM DesiredField df where df.mento.mentoId = :mentoId")
    List<DesiredField> findByMentoId(@Param("mentoId") Long mentoId);

    @Query("SELECT df FROM DesiredField df where df.field.fieldName in :fieldNames AND df.mento.mentoId is not null")
    List<DesiredField> findRecommendMentis(@Param("fieldNames") List<String> fieldNames);
}
