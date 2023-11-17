package com.theZ.dotoring.app.mento.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.mento.dto.*;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.mento.mapper.MentoMapper;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import com.theZ.dotoring.app.profile.model.Profile;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.common.URLConverter;
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
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Mento에관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MentoService {

    private final MentoRepository mentoRepository;
    private final URLConverter urlConverter;


    /**
     * 인자로 받은 매개변수들을 사용하여 Mento 엔티티를 생성하여 이를 DB에 저장한 후 반환하는 메서드
     *
     * @param saveMentoRqDTO
     * @param memberAccount
     * @parma profile
     * @param desiredFields
     * @param memberMajors
     *
     */
    public void saveMento(SaveMentoRqDTO saveMentoRqDTO, MemberAccount memberAccount, Profile profile, List<DesiredField> desiredFields, List<MemberMajor> memberMajors){
        Mento mento = Mento.createMento(saveMentoRqDTO.getNickname(), saveMentoRqDTO.getIntroduction(), saveMentoRqDTO.getSchool(), saveMentoRqDTO.getGrade(), memberAccount,profile,desiredFields,memberMajors);
        mentoRepository.save(mento);
    }

    /**
     * 멘토의 닉네임을 중복 확인 하는 메서드
     *
     * @parma validateMentoNicknameRqDTO
     * @Exception NicknameDuplicateException - 닉네임이 중복됬을 시 예외 발생
     */

    public void validateNickname(ValidateMentoNicknameRqDTO validateMentoNicknameRqDTO) {
        mentoRepository.findAll().stream().forEach(i ->{
            if(i.getNickname().equals(validateMentoNicknameRqDTO.getNickname())){
                throw new NicknameDuplicateException(MessageCode.DUPLICATED_NICKNAME);
            }
        });
    }

    /**
     * 멘토 상세 조회하고, 조회수가 올라가고, findMentiByIdRespDTO를 반환하는 메서드
     *
     * @parma mentoId
     *
     * @retrun findMentoByIdRespDTO
     */
    @Transactional(readOnly = true)
    public FindMentoByIdRespDTO findMentoWithProfile(Long mentoId){
        Mento mento = mentoRepository.findMentoWithProfileUsingFetchJoinByMentoId(mentoId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멘토입니다."));
        mento.updateViewCount();
        FindMentoByIdRespDTO findMentoByIdDTO = MentoMapper.fromDetail(mento);
        FindMentoByIdRespDTO findMentoByIdRespDTO = urlConverter.getFindMentoRespDTO(findMentoByIdDTO);
        return findMentoByIdRespDTO;
    }

    /**
     * 멘토 상세 메서드
     *
     * @parma mentoId
     *
     * @retrun Mento
     */
    @Transactional(readOnly = true)
    public Mento findMento(Long mentoId){
        return mentoRepository.findById(mentoId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멘토입니다."));
    }

    /**
     * mentiId가 일치하는 Mento 엔티티들을 DB에서 조회하여 findAllMentoRespDTOList로 변환후 반환하는 메서드
     *
     * @parma mentoIds
     *
     * @retrun findAllMentoRespDTOList
     */

    @Transactional(readOnly = true)
    public List<FindAllMentoRespDTO> findRecommendMentos(List<Long> mentoIds){
        List<Mento> recommendMentos = mentoRepository.findMentosWithProfileAndFieldsAndMajorsUsingFetchJoinByMentoId(mentoIds, Status.ACTIVE);
        return getSortedRecommendMentos(mentoIds, recommendMentos);
    }

    private List<FindAllMentoRespDTO> getSortedRecommendMentos(List<Long> mentoIds, List<Mento> recommendMentos) {
        List<Mento> sortedRecommendMentos = mentoIds.stream()
                .map(id -> recommendMentos.stream()
                        .filter(mento -> mento.getMentoId().equals(id))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return MentoMapper.from(sortedRecommendMentos);
    }


    /**
     * 대기 멘토들을 생성 순으로 페이징하여 반환하는 메서드
     *
     * @parma pageable
     *
     * @retrun FindWaitMentoRespDTO
     */

    @Transactional(readOnly = true)
    public Page<FindWaitMentoRespDTO> findWaitMentos(Pageable pageable){
        Sort sort = Sort.by("createdAt");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Mento> pagingMento = mentoRepository.findMentosByStatus(Status.WAIT, pageRequest);
        Page<FindWaitMentoRespDTO> findWaitMentoRespDTOS = MentoMapper.fromWaitMento(pagingMento);
        return findWaitMentoRespDTOS;
    }

    /**
     * mentoId가 일치하는 Mento 엔티티들을 DB에서 조회한 후 멘토링 수행 방법을 수정하는 메서드
     *
     * @parma updateMentoringSystemRqDTO
     *
     * @retrun findMentoByIdRespDTO
     */

    public FindMentoByIdRespDTO updateMentoringSystem(UpdateMentoringSystemRqDTO updateMentoringSystemRqDTO){
        Mento mento = mentoRepository.findById(updateMentoringSystemRqDTO.getMentoId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateMentoringSystem(updateMentoringSystemRqDTO.getMentoringSystem());
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    /**
     * mentoId가 일치하는 Mento 엔티티들을 DB에서 조회한 후 소개글 수정하는 메서드
     *
     * @parma updateMentoIntroductionRqDTO
     *
     * @retrun findMentoByIdRespDTO
     */

    public FindMentoByIdRespDTO updateIntroduction(UpdateMentoIntroductionRqDTO updateMentoIntroductionRqDTO) {
        Mento mento = mentoRepository.findById(updateMentoIntroductionRqDTO.getMentoId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateIntroduction(updateMentoIntroductionRqDTO.getIntroduction());
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    /**
     * mentoId가 일치하는 Mento 엔티티들을 DB에서 조회한 후 닉네임을 수정하는 메서드
     *
     * @parma updateMentoNicknameRqDTO
     *
     * @retrun findMentoByIdRespDTO
     */
    public FindMentoByIdRespDTO updateNickname(UpdateMentoNicknameRqDTO updateMentoNicknameRqDTO) {
        Mento mento = mentoRepository.findById(updateMentoNicknameRqDTO.getMentoId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateNickname(updateMentoNicknameRqDTO.getNickname());
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    /**
     * mentoId가 일치하는 Mento 엔티티들을 DB에서 조회한 후 선호 멘토링 분야들을 수정하는 메서드
     *
     * @parma desiredFields
     * @parma mentoId
     *
     * @retrun findMentoByIdRespDTO
     */
    public FindMentoByIdRespDTO updateDesiredFields(List<DesiredField> desiredFields, Long mentoId) {
        Mento mento = mentoRepository.findMentoWithProfileAndMajorsUsingFetchJoinByMentoId(mentoId).orElseThrow(() -> new IllegalStateException("존재하지 않는 멘토입니다."));
        mento.updateDesiredField(desiredFields);
        FindMentoByIdRespDTO findMentoByIdRespDTO = MentoMapper.fromDetail(mento);
        return findMentoByIdRespDTO;
    }

    /**
     * mentoId가 일치하는 Mento 엔티티들을 DB에서 조회한 후 대기 상태를 활동 상태로 바꿔주는 메서드
     *
     * @parma approveWaitMentosRqDTO
     *
     */
    public void approveWaitMentos(ApproveWaitMentosRqDTO approveWaitMentosRqDTO) {
        List<Mento> mentos = mentoRepository.findAllById(approveWaitMentosRqDTO.getMentoIds());
        mentos.stream().forEach(i -> i.approveStatus());
    }
}