package com.theZ.dotoring.app.menti.mapper;

import com.theZ.dotoring.app.menti.dto.MentiCardResponseDTO;
import com.theZ.dotoring.app.menti.model.Menti;

import java.util.stream.Collectors;

public class MentiMapper {

    public static MentiCardResponseDTO from(Menti menti){

        return MentiCardResponseDTO.builder()
                .id(menti.getMentiId())
                .nickname(menti.getNickname())
                .preferredMentoringSystem(menti.getPreferredMentoring())
                .introduction(menti.getIntroduction())
                .profileImage(menti.getProfile().getSavedProfileName())
                .majors(menti.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(menti.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .build();
    }

}
