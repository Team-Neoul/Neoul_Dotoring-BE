package com.theZ.dotoring.app.memberAccount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberEmailCodeResponseDTO {

    private String emailVerificationCode;

}
