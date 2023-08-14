package com.theZ.dotoring.app.mento.mapper;

import com.theZ.dotoring.app.mento.dto.MentoCardResponseDTO;
import com.theZ.dotoring.app.mento.model.Mento;

import java.util.stream.Collectors;

public class MentoMapper {

    public static MentoCardResponseDTO from(Mento mento){

        return MentoCardResponseDTO.builder()
                .id(mento.getMentoId())
                .nickname(mento.getNickname())
                .mentoringSystem(mento.getMentoringSystem())
                .introduction(mento.getIntroduction())
                .profileImage(mento.getProfile().getSavedProfileName())
                .majors(mento.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(mento.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .build();
    }
}
