package com.theZ.dotoring.app.mento.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempMento {

    @Id
    private Long tempMentoId;

    private String fields;

    private String majors;

    private Long grade;

    private String school;

    private String originalFileName;

    private String saveFileName;

    @Builder
    public TempMento(Long tempMentoId, String fields, String majors, Long grade, String school, String originalFileName, String saveFileName) {
        this.tempMentoId = tempMentoId;
        this.fields = fields;
        this.majors = majors;
        this.grade = grade;
        this.school = school;
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
    }

    public static TempMento createTempMento(Long tempMentoId, String fields, String majors, Long grade, String school, String originalFileName, String saveFileName) {
        return TempMento.builder()
                .tempMentoId(tempMentoId)
                .fields(fields)
                .majors(majors)
                .grade(grade)
                .school(school)
                .originalFileName(originalFileName)
                .saveFileName(saveFileName)
                .build();
    }

}

