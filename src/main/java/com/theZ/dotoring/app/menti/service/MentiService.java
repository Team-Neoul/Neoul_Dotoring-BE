package com.theZ.dotoring.app.menti.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.menti.dto.*;
import com.theZ.dotoring.app.menti.mapper.MentiMapper;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.enums.Status;
import com.theZ.dotoring.exception.NicknameDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Menti에관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MentiService {

    private final MentiRepository mentiRepository;

    /**
     * 인자로 받은 매개변수들을 사용하여 Menti 엔티티를 생성하여 이를 DB에 저장한 후 반환하는 메서드
     *
     * @param saveMentiRqDTO
     * @param memberAccount
     * @parma profile
     * @param desiredFields
     * @param memberMajors
     *
     */
    public void saveMenti(SaveMentiRqDTO saveMentiRqDTO, MemberAccount memberAccount, Profile profile, List<DesiredField> desiredFields, List<MemberMajor> memberMajors){
        Menti menti = Menti.createMenti(saveMentiRqDTO.getNickname(), saveMentiRqDTO.getIntroduction(), saveMentiRqDTO.getSchool(), saveMentiRqDTO.getGrade(), memberAccount,profile,desiredFields,memberMajors);
        mentiRepository.save(menti);
    }

    public void validateNickname(ValidateMentiNicknameRqDTO mentiNicknameRequestDTO) {
        mentiRepository.findAll().stream().forEach(i ->{
            if(i.getNickname().equals(mentiNicknameRequestDTO.getNickname())){
                throw new NicknameDuplicateException(MessageCode.DUPLICATED_NICKNAME);
            }
        });
    }

    public FindMentiByIdRespDTO findMentiWithProfile(Long mentiId){
        Menti menti = mentiRepository.findMentiWithProfileUsingFetchJoinByMentiId(mentiId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멘티입니다."));
        FindMentiByIdRespDTO findMentiByIdRespDTO = MentiMapper.fromDetail(menti);
        menti.updateViewCount();
        return findMentiByIdRespDTO;
    }

    public Menti findMenti(Long mentiId){
        return mentiRepository.findById(mentiId).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘티입니다."));
    }

    @Transactional(readOnly = true)
    public Page<FindWaitMentiRespDTO> findWaitMentis(Pageable pageable){
        Sort sort = Sort.by("createdAt");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Menti> pagingMenti = mentiRepository.findMentisByStatus(Status.WAIT, pageRequest);
        Page<FindWaitMentiRespDTO> findWaitMentiRespDTOS = MentiMapper.fromWaitMenti(pagingMenti);
        return findWaitMentiRespDTOS;
    }


    @Transactional(readOnly = true)
    public List<FindAllMentiRespDTO> findRecommendMentis(List<Long> mentiIds){
        List<Menti> recommendMentis = mentiRepository.findMentisWithProfileAndFieldsAndMajorsUsingFetchJoinByMentoId(mentiIds);
        List<FindAllMentiRespDTO> findAllMentiRespDTOList = MentiMapper.from(recommendMentis);
        return findAllMentiRespDTOList;
    }

    public FindAllMentiRespDTO updatePreferredMentoring(UpdateMentiMentoringSystemRqDTO updateMentiMentoringSystemRqDTO){
        Menti menti = mentiRepository.findById(updateMentiMentoringSystemRqDTO.getMentiId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘티입니다."));
        menti.updatePreferredMentoring(updateMentiMentoringSystemRqDTO.getPreferredMentoring());
        FindAllMentiRespDTO findAllMentiRespDTO = MentiMapper.fromCard(menti);
        return findAllMentiRespDTO;
    }

    public FindMentiByIdRespDTO updateItroduction(UpdateMentiIntroductionRqDTO updateMentiIntroduction) {
        Menti menti = mentiRepository.findById(updateMentiIntroduction.getMentiId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘티입니다."));
        menti.updateIntroduction(updateMentiIntroduction.getIntroduction());
        FindMentiByIdRespDTO findMentiByIdRespDTO = MentiMapper.fromDetail(menti);
        return findMentiByIdRespDTO;
    }

    public FindMentiByIdRespDTO updateNickname(UpdateMentiNicknameRqDTO updateMentiNicknameRqDTO) {
        Menti menti = mentiRepository.findById(updateMentiNicknameRqDTO.getMentiId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘티입니다."));
        menti.updateNickname(updateMentiNicknameRqDTO.getNickname());
        FindMentiByIdRespDTO findMentiByIdRespDTO = MentiMapper.fromDetail(menti);
        return findMentiByIdRespDTO;

    }

    public FindMentiByIdRespDTO updateDesiredFields(List<DesiredField> desiredFields, Long mentiId) {
        Menti menti = mentiRepository.findMentiWithProfileAndMajorsUsingFetchJoinByMentoId(mentiId).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘티입니다."));
        menti.updateDesiredField(desiredFields);
        FindMentiByIdRespDTO findMentiByIdRespDTO = MentiMapper.fromDetail(menti);
        return findMentiByIdRespDTO;
    }


    public void approveWaitMentis(ApproveWaitMentisRqDTO approveWaitMentisRqDTO) {
        List<Menti> mentis = mentiRepository.findAllById(approveWaitMentisRqDTO.getMentiIds());
        mentis.stream().forEach(i -> i.approveStatus());
    }
}
