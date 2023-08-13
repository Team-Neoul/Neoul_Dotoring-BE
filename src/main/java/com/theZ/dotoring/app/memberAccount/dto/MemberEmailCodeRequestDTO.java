package com.theZ.dotoring.app.memberAccount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEmailCodeRequestDTO {

    @Email
    private String email;
    private String emailVerificationCode;
}
