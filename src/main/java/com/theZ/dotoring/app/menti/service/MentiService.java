package com.theZ.dotoring.app.menti.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.menti.dto.MentiCardResponseDTO;
import com.theZ.dotoring.app.menti.dto.MentiNicknameRequestDTO;
import com.theZ.dotoring.app.menti.dto.MentiSignupRequestDTO;
import com.theZ.dotoring.app.menti.mapper.MentiMapper;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.mento.dto.MentoCardResponseDTO;
import com.theZ.dotoring.app.mento.mapper.MentoMapper;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.profile.model.Profile;
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
@Transactional
public class MentiService {

    private final MentiRepository mentiRepository;

    public void saveMenti(MentiSignupRequestDTO mentiSignupRequestDTO, MemberAccount memberAccount, Profile profile, List<DesiredField> desiredFields, List<MemberMajor> memberMajors){
        Menti menti = Menti.createMenti(mentiSignupRequestDTO.getNickname(), mentiSignupRequestDTO.getIntroduction(), mentiSignupRequestDTO.getSchool(), mentiSignupRequestDTO.getGrade(), memberAccount,profile,desiredFields,memberMajors);
        mentiRepository.save(menti);
    }

    public void validateNickname(MentiNicknameRequestDTO mentiNicknameRequestDTO) {
        mentiRepository.findAll().stream().forEach(i ->{
            if(i.getNickname().equals(mentiNicknameRequestDTO.getNickname())){
                throw new NicknameDuplicateException(MessageCode.DUPLICATED_NICKNAME);
            }
        });
    }

    public MentiCardResponseDTO findMenti(Long mentiId){
        Menti menti = mentiRepository.findMentiWithProfileUsingFetchJoinByMentiId(mentiId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 멘티입니다."));
        MentiCardResponseDTO mentiCardResponseDTO = MentiMapper.from(menti);
        menti.updateViewCount();
        return mentiCardResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<MentiCardResponseDTO> findRecommendMentis(List<Long> mentiIds){
        List<Menti> recommendMentis = mentiRepository.findMentisWithProfileAndFieldsAndMajorsUsingFetchJoinByMentoId(mentiIds);
        List<MentiCardResponseDTO> mentiCardResponseDTOList = MentiMapper.from(recommendMentis);
        return mentiCardResponseDTOList;
    }


}
