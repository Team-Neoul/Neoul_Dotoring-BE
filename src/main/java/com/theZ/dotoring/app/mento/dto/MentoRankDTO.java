package com.theZ.dotoring.app.mento.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MentoRankDTO {

    private Long mentoId;
    private Long mentoCount;

    @Builder
    public MentoRankDTO(Long mentoId, Long mentoCount) {
        this.mentoId = mentoId;
        this.mentoCount = mentoCount;
    }
}
