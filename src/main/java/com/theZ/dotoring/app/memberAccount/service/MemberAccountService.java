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

/**
 * MemberAccount 관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberAccountService {

    private final MemberAccountRepository memberAccountRepository;


    /**
     * 인자로 받은 mentoSignupRequestDTO와 certificates를 사용하여 MemberAccount 엔티티를 생성 후 DB에 저장한 후 이를 반환하는 메서드
     *
     * @param mentoSignupRequestDTO
     * @param certificates
     *
     * @return memberAccount
     */

    public MemberAccount saveMentoAccount(SaveMentoRqDTO mentoSignupRequestDTO, List<Certificate> certificates){
        MemberAccount memberAccount = MemberAccount.createMemberAccount(mentoSignupRequestDTO.getLoginId(), mentoSignupRequestDTO.getPassword(), mentoSignupRequestDTO.getEmail(), MemberType.MENTO, certificates);
        memberAccountRepository.save(memberAccount);
        return memberAccount;
    }

    /**
     * 인자로 받은 saveMentoRqDTO certificates를 사용하여 MemberAccount 엔티티를 생성 후 DB에 저장한 후 이를 반환하는 메서드
     *
     * @param saveMentiRqDTO
     * @param certificates
     *
     * @return memberAccount
     */
    public MemberAccount saveMentiAccount(SaveMentiRqDTO saveMentiRqDTO, List<Certificate> certificates){
        MemberAccount memberAccount = MemberAccount.createMemberAccount(saveMentiRqDTO.getLoginId(), saveMentiRqDTO.getPassword(), saveMentiRqDTO.getEmail(),MemberType.MENTI, certificates);
        memberAccountRepository.save(memberAccount);
        return memberAccount;
    }

    /**
     * 인자로 받은 loginId를 사용하여 MemberAccount 엔티티 행들 중 동일한 LoginId가 있는 지 확인하는 메서드
     *
     * @param loginId
     * @Exception - LoginIdDuplicateException 로그인 아이디가 중복한 것이 있을 때 발생하는 예외
     *
     */

    @Transactional(readOnly = true)
    public void checkDuplicateAboutLoginId(String loginId) {
        if(memberAccountRepository.findByLoginId(loginId).isPresent()){
            throw new LoginIdDuplicateException(MessageCode.DUPLICATED_LOGIN_ID);
        }
    }

    /**
     * 인자로 받은 email을 사용하여 MemberAccount 엔티티를 찾아온 후 로그인 아이디를 찾아 반환하는 메서드
     *
     * @param email
     *
     * @return loginId
     */
    @Transactional(readOnly = true)
    public String findLoginId(String email){
        MemberAccount memberAccount = memberAccountRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException(MessageCode.EMAIL_NOT_FOUND.getValue()));
        return memberAccount.getLoginId();
    }

    /**
     * 인자로 받은 email을 사용하여 MemberAccount 엔티티를 찾아온 후 해당 로그인 아이디가 존재하는 지를 확인한는 메서드
     *
     * @param memberPasswordRequestDTO
     * @Exception IllegalArgumentException - 아이디가 일치하지 않을 때 반환하는 예외
     */
    public void validateLoginIdForEmail(MemberPasswordRequestDTO memberPasswordRequestDTO){
        MemberAccount memberAccount = memberAccountRepository.findByEmail(memberPasswordRequestDTO.getEmail()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 이메일입니다."));
        String loginId = memberAccount.getLoginId();
        if(!loginId.equals(memberPasswordRequestDTO.getLoginId())){
            throw new IllegalArgumentException("아이디가 일치하지 않습니다.");
        }
    }

    /**
     * 인자로 받은 email을 사용하여 MemberAccount 엔티티를 찾아온 후 비밀번호를 수정하는 메서드
     *
     * @param email
     * @parma password
     *
     */
    public void updatePasswordByEmail(String email, String password) {
        MemberAccount memberAccount = memberAccountRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("존재하지 않는 이메일입니다."));
        memberAccount.updatePassword(password);
    }

    /**
     * MemberAccount를 통해 Mento인지 Menti인지를 판별 할 수 있는 메서드
     *
     * @param memberId
     *
     * @return Mento일 경우에는 true, Menti일 경우에는 false 반환
     */

    public Boolean isMento(Long memberId){
        MemberAccount memberAccount = memberAccountRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        if(memberAccount.getMemberType() == MemberType.MENTO){
            return true;
        }
        return false;
    }

    /**
     * memberId(mentoId or mentiId)를 사용해 MemberAccount엔티티를 조회한 후 로그인 아이디를 수정하는 메서드
     *
     * @param updateMemberLoginIdRequestDTO
     *
     */
    public void updateLoginId(UpdateMemberLoginIdRequestDTO updateMemberLoginIdRequestDTO) {
        MemberAccount memberAccount = memberAccountRepository.findById(updateMemberLoginIdRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        memberAccount.updateLoginId(updateMemberLoginIdRequestDTO.getLoginId());
    }

    /**
     * memberId(mentoId or mentiId)를 사용해 MemberAccount엔티티를 조회한 후 로그인 비밀번호를 수정하는 메서드
     *
     * @param updateMemberPasswordRequestDTO
     *
     */
    public void updatePassword(UpdateMemberPasswordRequestDTO updateMemberPasswordRequestDTO) {
        MemberAccount memberAccount = memberAccountRepository.findById(updateMemberPasswordRequestDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
        memberAccount.updatePassword(updateMemberPasswordRequestDTO.getPassword());
    }

}
