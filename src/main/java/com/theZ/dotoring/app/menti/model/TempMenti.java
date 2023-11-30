package com.theZ.dotoring.app.menti.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempMenti {

    @Id
    private Long tempMentiId;

    private String fields;

    private String majors;

    private Long grade;

    private String school;

    private String originalFileName;

    private String saveFileName;

    @Builder
    public TempMenti(Long tempMentiId, String fields, String majors, Long grade, String school, String originalFileName, String saveFileName) {
        this.tempMentiId = tempMentiId;
        this.fields = fields;
        this.majors = majors;
        this.grade = grade;
        this.school = school;
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
    }

    public static TempMenti createTempMenti(Long tempMentiId, String fields, String majors, Long grade, String school, String originalFileName, String saveFileName) {
        return TempMenti.builder()
                .tempMentiId(tempMentiId)
                .fields(fields)
                .majors(majors)
                .grade(grade)
                .school(school)
                .originalFileName(originalFileName)
                .saveFileName(saveFileName)
                .build();
    }

}


