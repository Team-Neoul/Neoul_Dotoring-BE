package com.theZ.dotoring.app.memberAccount.service;

import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.repository.MemberAccountRepository;
import com.theZ.dotoring.app.mento.dto.MentoSignupRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberAccountService {

    private final MemberAccountRepository memberAccountRepository;

    public MemberAccount saveMentoAccount(MentoSignupRequestDTO mentoSignupRequestDTO, List<Certificate> certificates){
        MemberAccount memberAccount = MemberAccount.createMemberAccount(mentoSignupRequestDTO.getLoginId(), mentoSignupRequestDTO.getPassword(), mentoSignupRequestDTO.getEmail(), certificates);
        memberAccountRepository.save(memberAccount);
        return memberAccount;
    }

}
