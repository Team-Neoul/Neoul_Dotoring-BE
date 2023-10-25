package com.theZ.dotoring.app.memberAccount.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MENTO("ROLE_MENTO"),
    ROLE_MENTI("ROLE_MENTI"),
    ROLE_WAIT("ROLE_WAIT");

    private final String type;
}
