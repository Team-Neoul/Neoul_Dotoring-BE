package com.theZ.dotoring.app.mento.mapper;

import com.theZ.dotoring.app.mento.dto.FindAllMentoRespDTO;
import com.theZ.dotoring.app.mento.dto.FindMentoByIdRespDTO;
import com.theZ.dotoring.app.mento.dto.FindWaitMentoRespDTO;
import com.theZ.dotoring.app.mento.model.Mento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MentoMapper {

    private static String url;
    @Value("${url}")
    public void setUrl(String url) {
        this.url = url;
    }
    public static FindAllMentoRespDTO fromCard(Mento mento){

        return FindAllMentoRespDTO.builder()
                .id(mento.getMentoId())
                .nickname(mento.getNickname())
                .mentoringSystem(mento.getMentoringSystem())
                .introduction(mento.getIntroduction())
                .profileImage(makeUrl(mento.getProfile().getProfilePath()))
                .majors(mento.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(mento.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .build();
    }

    public static FindMentoByIdRespDTO fromDetail(Mento mento){

        FindMentoByIdRespDTO findMentoByIdRespDTO = FindMentoByIdRespDTO.builder()
                .mentoId(mento.getMentoId())
                .nickname(mento.getNickname())
                .mentoringSystem(mento.getMentoringSystem())
                .introduction(mento.getIntroduction())
                .profileImage(makeUrl(mento.getProfile().getProfilePath()))
                .majors(mento.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(mento.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .grade(mento.getGrade())
                .build();
        return findMentoByIdRespDTO;
    }



    public static List<FindAllMentoRespDTO> from(List<Mento> mentos){

        List<FindAllMentoRespDTO> findAllMentoRespDTOS = new ArrayList<>();

        IntStream.range(0, mentos.size())
                .forEach(i -> findAllMentoRespDTOS.add(
                        FindAllMentoRespDTO.builder()
                            .id(mentos.get(i).getMentoId())
                            .nickname(mentos.get(i).getNickname())
                            .mentoringSystem(mentos.get(i).getMentoringSystem())
                            .introduction(mentos.get(i).getIntroduction())
                            .profileImage(makeUrl(mentos.get(i).getProfile().getProfilePath()))
                            .majors(mentos.get(i).getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                            .fields(mentos.get(i).getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                            .build())
                );
        return findAllMentoRespDTOS;
    }

    public static Page<FindWaitMentoRespDTO> fromWaitMento(Page<Mento> pagingMento) {

        List<FindWaitMentoRespDTO> findWaitMentoRespDTOS = new ArrayList<>();

        IntStream.range(0,pagingMento.getContent().size())
                .forEach(i -> findWaitMentoRespDTOS.add(
                        FindWaitMentoRespDTO.builder()
                                .nickname(pagingMento.getContent().get(i).getNickname())
                                .grade(pagingMento.getContent().get(i).getGrade())
                                .school(pagingMento.getContent().get(i).getSchool())
                                .build()
                ));

        Page<FindWaitMentoRespDTO> findWaitMentoPagindRespDTOS = new PageImpl<>(findWaitMentoRespDTOS,pagingMento.getPageable(),pagingMento.getTotalPages());
        return findWaitMentoPagindRespDTOS;
    }

    private static String makeUrl(String imageUri) {
        return url + imageUri;
    }
}
