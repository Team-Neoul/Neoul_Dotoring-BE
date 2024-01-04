package com.theZ.dotoring.app.menti.mapper;

import com.theZ.dotoring.app.menti.dto.FindAllMentiRespDTO;
import com.theZ.dotoring.app.menti.dto.FindMentiByIdRespDTO;
import com.theZ.dotoring.app.menti.dto.FindMyMentiRespDTO;
import com.theZ.dotoring.app.menti.dto.FindWaitMentiRespDTO;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.common.StringListUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class MentiMapper {

    public static FindAllMentiRespDTO fromCard(Menti menti){

        return FindAllMentiRespDTO.builder()
                .id(menti.getMentiId())
                .nickname(menti.getNickname())
                .preferredMentoringSystem(menti.getPreferredMentoring())
                .tags(StringListUtils.split(menti.getTags()))
                .profileImage(menti.getProfile().getSavedProfileName())
                .majors(menti.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(menti.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .build();
    }

    public static FindMentiByIdRespDTO fromDetail(Menti menti){
        FindMentiByIdRespDTO findMentiByIdRespDTO = FindMentiByIdRespDTO.builder()
                .mentiId(menti.getMentiId())
                .nickname(menti.getNickname())
                .preferredMentoring(menti.getPreferredMentoring())
                .tags(StringListUtils.split(menti.getTags()))
                .profileImage(menti.getProfile().getSavedProfileName())
                .majors(menti.getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                .fields(menti.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .grade(menti.getGrade())
                .build();

        return findMentiByIdRespDTO;
    }
    public static List<FindAllMentiRespDTO> from(List<Menti> mentis){

        List<FindAllMentiRespDTO> findAllMentiRespDTOS = new ArrayList<>();

        IntStream.range(0, mentis.size())
                .forEach(i -> findAllMentiRespDTOS.add(
                        FindAllMentiRespDTO.builder()
                                .id(mentis.get(i).getMentiId())
                                .nickname(mentis.get(i).getNickname())
                                .preferredMentoringSystem(mentis.get(i).getPreferredMentoring())
                                .tags(StringListUtils.split(mentis.get(i).getTags()))
                                .profileImage(mentis.get(i).getProfile().getSavedProfileName())
                                .majors(mentis.get(i).getMemberMajors().stream().map(m -> m.getMajor().getMajorName()).collect(Collectors.toList()))
                                .fields(mentis.get(i).getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                                .build())
                );
        return findAllMentiRespDTOS;
    }

    public static Page<FindWaitMentiRespDTO> fromWaitMenti(Page<Menti> pagingMenti) {

        List<FindWaitMentiRespDTO> findWaitMentiRespDTOS = new ArrayList<>();

        IntStream.range(0,pagingMenti.getContent().size())
                .forEach(i -> findWaitMentiRespDTOS.add(
                        FindWaitMentiRespDTO.builder()
                                .nickname(pagingMenti.getContent().get(i).getNickname())
                                .grade(pagingMenti.getContent().get(i).getGrade())
                                .school(pagingMenti.getContent().get(i).getSchool())
                                .build()
                ));

        Page<FindWaitMentiRespDTO> findWaitMentiPagindRespDTOS = new PageImpl<>(findWaitMentiRespDTOS,pagingMenti.getPageable(),pagingMenti.getTotalPages());
        return findWaitMentiPagindRespDTOS;
    }

    public static FindMyMentiRespDTO fromMyMenti(Menti menti) {
        return FindMyMentiRespDTO.builder()
                .mentiId(menti.getMentiId())
                .nickname(menti.getNickname())
                .profileImage(menti.getProfile().getSavedProfileName())
                .fields(menti.getDesiredFields().stream().map(desiredField -> desiredField.getField().getFieldName()).collect(Collectors.toList()))
                .majors(menti.getMemberMajors().stream().map(memberMajor -> memberMajor.getMajor().getMajorName()).collect(Collectors.toList()))
                .tags(StringListUtils.split(menti.getTags()))
                .grade(menti.getGrade())
                .preferredMentoring(menti.getPreferredMentoring())
                .school(menti.getSchool())
                .build();
    }
}
