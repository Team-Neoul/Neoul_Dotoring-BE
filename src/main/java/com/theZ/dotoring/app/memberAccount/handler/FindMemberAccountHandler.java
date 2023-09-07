package com.theZ.dotoring.app.memberAccount.handler;

import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import com.theZ.dotoring.app.memberAccount.service.MemberEmailService;
import com.theZ.dotoring.app.mento.dto.EmailCodeRequestDTO;
import com.theZ.dotoring.app.mento.dto.MemberPasswordRequestDTO;
import com.theZ.dotoring.common.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMemberAccountHandler {

    private final MemberAccountService memberAccountService;
    private final MemberEmailService memberEmailService;
    private final RedisUtil redisUtil;

    /**
     * MemberAccount를 통해 Mento인지 Menti인지를 판별 할 수 있는 메서드
     *
     * @param emailCodeRequestDTO
     *
     * @return loginId
     */

    public String executeForLoginId(EmailCodeRequestDTO emailCodeRequestDTO){
        String email = memberEmailService.validateCode(emailCodeRequestDTO.getCode(),emailCodeRequestDTO.getEmail());
        redisUtil.deleteData(emailCodeRequestDTO.getCode());
        return memberAccountService.findLoginId(email);
    }


    public void executeForPassword(MemberPasswordRequestDTO memberPasswordRequestDTO) {

        /**
         *  로그인 아이디를 사용해, 이메일을 찾는다. 후에 이 이메일이 요청받은 이메일과 일치하는 지 확인한다. -> 오류가 있다면 에러 반환
         *  일치한다면, 이메일을 사용하여 비밀번호를 발송한 후에 회원의 비밀번호를 변경한다.
         */
        memberAccountService.validateLoginIdForEmail(memberPasswordRequestDTO);
        String password = makePassword();
        memberEmailService.sendPasswordByEmail(memberPasswordRequestDTO.getEmail(), password);
        memberAccountService.updatePasswordByEmail(memberPasswordRequestDTO.getEmail(),password);
    }

    private String makePassword(){
        return "dotoring1234";
    }
}
