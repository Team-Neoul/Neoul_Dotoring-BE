package com.theZ.dotoring.app.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDTO {
    private Long roomPK;

    private Long memberPK;

    private String nickname;

    private String lastLetter;

    private String major;

    // Room Entity 필드
    private LocalDateTime updateAt;

}