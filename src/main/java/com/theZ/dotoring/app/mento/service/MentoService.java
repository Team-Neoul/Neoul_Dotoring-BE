package com.theZ.dotoring.app.mento.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.menti.dto.UpdateMentoDesiredFieldRqDTO;
import com.theZ.dotoring.app.mento.dto.*;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.mento.mapper.MentoMapper;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.NicknameDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional()
public class MentoService {

    private final MentoRepository mentoRepository;


    public void saveMento(SaveMentoRqDTO saveMentoRqDTO, MemberAccount memberAccount, Profile profile, List<DesiredField> desiredFields, List<MemberMajor> memberMajors){
        Mento mento = Mento.createMento(saveMentoRqDTO.getNickname(), saveMentoRqDTO.getIntroduction(), saveMentoRqDTO.getSchool(), saveMentoRqDTO.getGrade(), memberAccount,profile,desiredFields,memberMajors);
        mentoRepository.save(mento);
    }

    public void validateNickname(ValidateMentoNicknameRqDTO validateMentoNicknameRqDTO) {
        mentoRepository.findAll().stream().forEach(i ->{
            if(i.getNickname().equals(validateMentoNicknameRqDTO.getNickname())){
                throw new NicknameDuplicateException(MessageCode.DUPLICATED_NICKNAME);
            }
        });
    }

    @Transactional(readOnly = true)
    public FindMentoByIdRespDTO findMentoByProfile(Long mentoId){
        Mento mento = mentoRepository.findMentoWithProfileUsingFetchJoinByMentoId(mentoId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멘토입니다."));
        mento.updateViewCount();
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    public Mento findMento(Long mentoId){
        return mentoRepository.findById(mentoId).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
    }

    @Transactional(readOnly = true)
    public List<FindAllMentoRespDTO> findRecommendMentos(List<Long> mentoIds){
        List<Mento> recommendMentos = mentoRepository.findMentosWithProfileAndFieldsAndMajorsUsingFetchJoinByMentoId(mentoIds);
        List<FindAllMentoRespDTO> findAllMentoRespDTOList = MentoMapper.from(recommendMentos);
        return findAllMentoRespDTOList;
    }

    public FindMentoByIdRespDTO updateMentoringSystem(UpdateMentoringSystemRqDTO updateMentoringSystemRqDTO){
        Mento mento = mentoRepository.findById(updateMentoringSystemRqDTO.getMentoId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateMentoringSystem(updateMentoringSystemRqDTO.getMentoringSystem());
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    public FindMentoByIdRespDTO updateIntroduction(UpdateMentoIntroductionRqDTO updateMentoIntroductionRqDTO) {
        Mento mento = mentoRepository.findById(updateMentoIntroductionRqDTO.getMentoId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateIntroduction(updateMentoIntroductionRqDTO.getIntroduction());
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    public FindMentoByIdRespDTO updateNickname(UpdateMentoNicknameRqDTO updateMentoNicknameRqDTO) {
        Mento mento = mentoRepository.findById(updateMentoNicknameRqDTO.getMentoId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateNickname(updateMentoNicknameRqDTO.getNickname());
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    public FindMentoByIdRespDTO updateDesiredFields(List<DesiredField> desiredFields, Long mentoId) {
        Mento mento = mentoRepository.findMentoWithProfileAndMajorsUsingFetchJoinByMentoId(mentoId).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateDesiredField(desiredFields);
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }


}
