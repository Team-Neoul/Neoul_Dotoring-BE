package com.theZ.dotoring.app.mento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class MemberPasswordRequestDTO {

    private String loginId;

    @Email(message = "이메일 패턴이 올바르지 않습니다.")
    private String email;
}
