package com.theZ.dotoring.app.mento.mapper;

import com.theZ.dotoring.app.mento.dto.MentoCardResponseDTO;
import com.theZ.dotoring.app.mento.model.Mento;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static List<MentoCardResponseDTO> from(List<Mento> mentos){

        List<MentoCardResponseDTO> mentoCardResponseDTOS = new ArrayList<>();

        IntStream.range(0, mentos.size())
                .forEach(i -> mentoCardResponseDTOS.add(
                        MentoCardResponseDTO.builder()
                            .id(mentos.get(i).getMentoId())
                            .nickname(mentos.get(i).getNickname())
                            .mentoringSystem(mentos.get(i).getMentoringSystem())
                            .introduction(mentos.get(i).getIntroduction())
                            .profileImage(mentos.get(i).getProfile().getSavedProfileName())
                            .majors(mentos.get(i).getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                            .fields(mentos.get(i).getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                            .build())
                );
        return mentoCardResponseDTOS;
    }
}
