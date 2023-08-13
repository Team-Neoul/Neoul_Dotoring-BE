package com.theZ.dotoring.app.memberAccount.service;

import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.mento.dto.MentoNicknameRequestDTO;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.repository.MemberAccountRepository;
import com.theZ.dotoring.app.menti.dto.MentiSignupRequestDTO;
import com.theZ.dotoring.app.mento.dto.MentoSignupRequestDTO;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.LoginIdDuplicateException;
import com.theZ.dotoring.exception.NicknameDuplicateException;
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

    public MemberAccount saveMentiAccount(MentiSignupRequestDTO mentiSignupRequestDTO, List<Certificate> certificates){
        MemberAccount memberAccount = MemberAccount.createMemberAccount(mentiSignupRequestDTO.getLoginId(), mentiSignupRequestDTO.getPassword(), mentiSignupRequestDTO.getEmail(), certificates);
        memberAccountRepository.save(memberAccount);
        return memberAccount;
    }

    public void validateNickname(MentoNicknameRequestDTO mentoNicknameRequestDTO) {
        memberAccountRepository.findAll().stream().forEach(i ->{
            if(i.getEmail().equals(mentoNicknameRequestDTO.getNickname())){
                throw new NicknameDuplicateException(MessageCode.DUPLICATED_NICKNAME);
            }
        });
    }

    public void validateLoginId(String loginId) {
        memberAccountRepository.findAll().stream().forEach(i -> {
            if(i.getLoginId().equals(loginId)){
                throw new LoginIdDuplicateException(MessageCode.DUPLICATED_LOGIN_ID);
            }
        });

    }


}
