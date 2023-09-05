package com.theZ.dotoring.app.letter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LetterByMemberResponseDTO {

    private Long letterId;

    private String content;

    private boolean writer;

    private String nickname;

    private Date createdAt;
}