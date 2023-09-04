package com.theZ.dotoring.app.memberAccount.dto;

import lombok.Data;

@Data
public class UpdateMemberPasswordRequestDTO {

    private String password;
    private Long memberId;


}
