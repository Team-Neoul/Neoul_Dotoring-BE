package com.theZ.dotoring.app.memberAccount.dto;

import lombok.Data;

@Data
public class UpdateMemberLoginIdRequestDTO {

    private String loginId;
    private Long memberId;

}
