package com.theZ.dotoring.app.menti.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.menti.dto.MentiSignupRequestDTO;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.mento.dto.MentoSignupRequestDTO;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.profile.model.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
