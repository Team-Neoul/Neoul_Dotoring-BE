package com.theZ.dotoring.app.memberAccount.service;

import com.theZ.dotoring.app.certificate.model.Certificate;
import com.theZ.dotoring.app.memberAccount.dto.UpdateMemberLoginIdRequestDTO;
import com.theZ.dotoring.app.memberAccount.dto.UpdateMemberPasswordRequestDTO;
import com.theZ.dotoring.app.mento.dto.MemberPasswordRequestDTO;
import com.theZ.dotoring.app.mento.dto.ValidateMentoNicknameRqDTO;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.repository.MemberAccountRepository;
import com.theZ.dotoring.app.menti.dto.SaveMentiRqDTO;
import com.theZ.dotoring.app.mento.dto.SaveMentoRqDTO;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.enums.MemberType;
import com.theZ.dotoring.exception.LoginIdDuplicateException;
import com.theZ.dotoring.exception.NicknameDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberAccountService {

    private final MemberAccountRepository memberAccountRepository;

    public MemberAccount saveMentoAccount(SaveMentoRqDTO mentoSignupRequestDTO, List<Certificate> certificates){
        MemberAccount memberAccount = MemberAccount.createMemberAccount(mentoSignupRequestDTO.getLoginId(), mentoSignupRequestDTO.getPassword(), mentoSignupRequestDTO.getEmail(), MemberType.MENTO, certificates);
        memberAccountRepository.save(memberAccount);
        return memberAccount;
    }

    public MemberAccount saveMentiAccount(SaveMentiRqDTO saveMentiRqDTO, List<Certificate> certificates){
        MemberAccount memberAccount = MemberAccount.createMemberAccount(saveMentiRqDTO.getLoginId(), saveMentiRqDTO.getPassword(), saveMentiRqDTO.getEmail(),MemberType.MENTI, certificates);
        memberAccountRepository.save(memberAccount);
        return memberAccount;
    }

    @Transactional(readOnly = true)
    public void checkDuplicateAboutNickname(ValidateMentoNicknameRqDTO validateMentoNicknameRqDTO) {
        memberAccountRepository.findAll().stream().forEach(i ->{
            if(i.getEmail().equals(validateMentoNicknameRqDTO.getNickname())){
                throw new NicknameDuplicateException(MessageCode.DUPLICATED_NICKNAME);
            }
        });
    }

    @Transactional(readOnly = true)
    public void checkDuplicateAboutLoginId(String loginId) {
        if(memberAccountRepository.findByLoginId(loginId).isPresent()){
            throw new LoginIdDuplicateException(MessageCode.DUPLICATED_LOGIN_ID);
        }
    }

    @Transactional(readOnly = true)
    public String findLoginId(String email){
        MemberAccount memberAccount = memberAccountRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("존재하지 않는 이메일입니다."));
        return memberAccount.getLoginId();
    }

    public void validateLoginIdForEmail(MemberPasswordRequestDTO memberPasswordRequestDTO){
        MemberAccount memberAccount = memberAccountRepository.findByEmail(memberPasswordRequestDTO.getEmail()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 이메일입니다."));
        String loginId = memberAccount.getLoginId();
        if(!loginId.equals(memberPasswordRequestDTO.getLoginId())){
            throw new IllegalArgumentException("아이디가 일치하지 않습니다.");
        }
    }

    @Transactional(readOnly = true)
    public Boolean ifPresentLoginId(String loginId){
        return memberAccountRepository.findByLoginId(loginId).isPresent();
    }


    public void updatePasswordByEmail(String email, String password) {
        MemberAccount memberAccount = memberAccountRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("존재하지 않는 이메일입니다."));
        memberAccount.updatePassword(password);
    }

    /**
     *
     * MemberAccount를 통해 Mento인지 Menti인지를 판별 할 수 있는 메서드
     */

    public Boolean isMento(Long memberId){
        MemberAccount memberAccount = memberAccountRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        if(memberAccount.getMemberType() == MemberType.MENTO){
            return true;
        }
        return false;
    }

    public void updateLoginId(UpdateMemberLoginIdRequestDTO updateMemberLoginIdRequestDTO) {
        MemberAccount memberAccount = memberAccountRepository.findById(updateMemberLoginIdRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        memberAccount.updateLoginId(updateMemberLoginIdRequestDTO.getLoginId());
    }

    public void updatePassword(UpdateMemberPasswordRequestDTO updateMemberPasswordRequestDTO) {
        MemberAccount memberAccount = memberAccountRepository.findById(updateMemberPasswordRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        memberAccount.updatePassword(updateMemberPasswordRequestDTO.getPassword());
    }

}
