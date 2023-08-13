package com.theZ.dotoring.app.memberAccount.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberEmailRequestDTO {

    @Email(message = "이메일 패턴이 올바르지 않습니다.")
    private String email;

}
