package com.theZ.dotoring.app.mento.mapper;

import com.theZ.dotoring.app.mento.dto.FindAllMentoRespDTO;
import com.theZ.dotoring.app.mento.dto.FindMentoByIdRespDTO;
import com.theZ.dotoring.app.mento.dto.FindMyMentoRespDTO;
import com.theZ.dotoring.app.mento.dto.FindWaitMentoRespDTO;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.common.TagUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MentoMapper {

    public static FindMentoByIdRespDTO fromDetail(Mento mento){

        FindMentoByIdRespDTO findMentoByIdRespDTO = FindMentoByIdRespDTO.builder()
                .mentoId(mento.getMentoId())
                .nickname(mento.getNickname())
                .mentoringSystem(mento.getMentoringSystem())
                .tags(TagUtils.splitTags(mento.getTags()))
                .profileImage(mento.getProfile().getSavedProfileName())
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
                            .tags(TagUtils.splitTags(mentos.get(i).getTags()))
                            .profileImage(mentos.get(i).getProfile().getSavedProfileName())
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

    public static FindMyMentoRespDTO fromMyMento(Mento mento) {
        FindMyMentoRespDTO findMyMentoRespDTO = FindMyMentoRespDTO.builder()
                .mentoId(mento.getMentoId())
                .nickname(mento.getNickname())
                .tags(TagUtils.splitTags(mento.getTags()))
                .profileImage(mento.getProfile().getSavedProfileName())
                .majors(mento.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(mento.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .grade(mento.getGrade())
                .mentoringSystem(mento.getMentoringSystem())
                .build();
        return findMyMentoRespDTO;
    }
}
