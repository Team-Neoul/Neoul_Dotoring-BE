package com.theZ.dotoring.common;

import com.theZ.dotoring.app.menti.dto.FindAllMentiRespDTO;
import com.theZ.dotoring.app.menti.dto.FindMentiByIdRespDTO;
import com.theZ.dotoring.app.menti.dto.FindMyMentiRespDTO;
import com.theZ.dotoring.app.mento.dto.FindAllMentoRespDTO;
import com.theZ.dotoring.app.mento.dto.FindMentoByIdRespDTO;
import com.theZ.dotoring.app.mento.dto.FindMyMentoRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class URLService {

    private final S3Connector s3Connector;

    public List<FindAllMentiRespDTO> getFindAllMentiRespDTOS(List<FindAllMentiRespDTO> recommendMentis) {
        return recommendMentis.stream().map(mentiRespDTO -> new FindAllMentiRespDTO(mentiRespDTO.getId(),
                s3Connector.getPreSignedUrl(mentiRespDTO.getProfileImage()),
                mentiRespDTO.getNickname(),
                mentiRespDTO.getPreferredMentoringSystem(),
                mentiRespDTO.getFields(),
                mentiRespDTO.getMajors(),
                mentiRespDTO.getTags())).collect(Collectors.toList());
    }

    public List<FindAllMentoRespDTO> getFindAllMentoRespDTOS(List<FindAllMentoRespDTO> recommendMentors) {
        return recommendMentors.stream().map(mentoRespDTO -> new FindAllMentoRespDTO(mentoRespDTO.getId(),
                s3Connector.getPreSignedUrl(mentoRespDTO.getProfileImage()),
                mentoRespDTO.getNickname(),
                mentoRespDTO.getMentoringSystem(),
                mentoRespDTO.getFields(),
                mentoRespDTO.getMajors(),
                mentoRespDTO.getTags())).collect(Collectors.toList());
    }

    public FindMentiByIdRespDTO getFindMentiRespDTO(FindMentiByIdRespDTO findMentiByIdRespDTO){
        return new FindMentiByIdRespDTO(findMentiByIdRespDTO.getMentiId(),
                s3Connector.getPreSignedUrl(findMentiByIdRespDTO.getProfileImage()),
                findMentiByIdRespDTO.getNickname(),
                findMentiByIdRespDTO.getPreferredMentoring(),
                findMentiByIdRespDTO.getFields(),
                findMentiByIdRespDTO.getMajors(),
                findMentiByIdRespDTO.getTags(),
                findMentiByIdRespDTO.getGrade());
    }

    public FindMentoByIdRespDTO getFindMentoRespDTO(FindMentoByIdRespDTO findMentoByIdRespDTO){
        return new FindMentoByIdRespDTO(findMentoByIdRespDTO.getMentoId(),
                s3Connector.getPreSignedUrl(findMentoByIdRespDTO.getProfileImage()),
                findMentoByIdRespDTO.getNickname(),
                findMentoByIdRespDTO.getMentoringSystem(),
                findMentoByIdRespDTO.getFields(),
                findMentoByIdRespDTO.getMajors(),
                findMentoByIdRespDTO.getTags(),
                findMentoByIdRespDTO.getGrade());
    }

    public FindMyMentoRespDTO getMyMentoRespDTO(FindMyMentoRespDTO findMyMentoRespDTO) {
        return new FindMyMentoRespDTO(findMyMentoRespDTO.getMentoId(),
                s3Connector.getPreSignedUrl(findMyMentoRespDTO.getProfileImage()),
                findMyMentoRespDTO.getNickname(),
                findMyMentoRespDTO.getFields(),
                findMyMentoRespDTO.getMajors(),
                findMyMentoRespDTO.getTags(),
                findMyMentoRespDTO.getGrade());
    }

    public FindMyMentiRespDTO getMyMentiRespDTO(FindMyMentiRespDTO findMyMentiRespDTO) {
        return new FindMyMentiRespDTO(findMyMentiRespDTO.getMentiId(),
                s3Connector.getPreSignedUrl(findMyMentiRespDTO.getProfileImage()),
                findMyMentiRespDTO.getNickname(),
                findMyMentiRespDTO.getFields(),
                findMyMentiRespDTO.getMajors(),
                findMyMentiRespDTO.getTags(),
                findMyMentiRespDTO.getGrade(),
                findMyMentiRespDTO.getPreferredMentoring());
    }
}
