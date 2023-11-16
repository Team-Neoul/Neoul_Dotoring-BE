package com.theZ.dotoring.common;

import com.theZ.dotoring.app.menti.dto.FindAllMentiRespDTO;
import com.theZ.dotoring.app.menti.dto.FindMentiByIdRespDTO;
import com.theZ.dotoring.app.mento.dto.FindAllMentoRespDTO;
import com.theZ.dotoring.app.mento.dto.FindMentoByIdRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class URLConverter {

    private final S3Service s3Service;

    public List<FindAllMentiRespDTO> getFindAllMentiRespDTOS(List<FindAllMentiRespDTO> recommendMentis) {
        return recommendMentis.stream().map(mentiRespDTO -> new FindAllMentiRespDTO(mentiRespDTO.getId(),
                s3Service.getPreSignedUrl(mentiRespDTO.getProfileImage()),
                mentiRespDTO.getNickname(),
                mentiRespDTO.getPreferredMentoringSystem(),
                mentiRespDTO.getFields(),
                mentiRespDTO.getMajors(),
                mentiRespDTO.getIntroduction())).collect(Collectors.toList());
    }

    public List<FindAllMentoRespDTO> getFindAllMentoRespDTOS(List<FindAllMentoRespDTO> recommendMentors) {
        return recommendMentors.stream().map(mentoRespDTO -> new FindAllMentoRespDTO(mentoRespDTO.getId(),
                s3Service.getPreSignedUrl(mentoRespDTO.getProfileImage()),
                mentoRespDTO.getNickname(),
                mentoRespDTO.getMentoringSystem(),
                mentoRespDTO.getFields(),
                mentoRespDTO.getMajors(),
                mentoRespDTO.getIntroduction())).collect(Collectors.toList());
    }

    public FindMentiByIdRespDTO getFindMentiRespDTO(FindMentiByIdRespDTO findMentiByIdRespDTO){
        return new FindMentiByIdRespDTO(findMentiByIdRespDTO.getMentiId(),
                s3Service.getPreSignedUrl(findMentiByIdRespDTO.getProfileImage()),
                findMentiByIdRespDTO.getNickname(),
                findMentiByIdRespDTO.getPreferredMentoring(),
                findMentiByIdRespDTO.getFields(),
                findMentiByIdRespDTO.getMajors(),
                findMentiByIdRespDTO.getIntroduction(),
                findMentiByIdRespDTO.getGrade());
    }

    public FindMentoByIdRespDTO getFindMentoRespDTO(FindMentoByIdRespDTO findMentoByIdRespDTO){
        return new FindMentoByIdRespDTO(findMentoByIdRespDTO.getMentoId(),
                s3Service.getPreSignedUrl(findMentoByIdRespDTO.getProfileImage()),
                findMentoByIdRespDTO.getNickname(),
                findMentoByIdRespDTO.getMentoringSystem(),
                findMentoByIdRespDTO.getFields(),
                findMentoByIdRespDTO.getMajors(),
                findMentoByIdRespDTO.getIntroduction(),
                findMentoByIdRespDTO.getGrade());
    }
}
