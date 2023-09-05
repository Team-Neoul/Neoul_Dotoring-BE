package com.theZ.dotoring.app.letter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LetterByMemberRequestDTO {

    @NotBlank(message = "빈 칸을 보낼 수 없습니다.")
    @Size(min = 2, max = 200, message = "문자의 최소 길이는 2글자이고, 문자의 최대 길이는 200 글자입니다.")
    private String content;
}