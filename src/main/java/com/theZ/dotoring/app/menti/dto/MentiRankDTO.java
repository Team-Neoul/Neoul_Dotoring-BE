package com.theZ.dotoring.app.menti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MentiRankDTO {

    private Long mentiId;
    private Long mentiCount;

    @Builder
    public MentiRankDTO(Long mentiId, Long mentiCount) {
        this.mentiId = mentiId;
        this.mentiCount = mentiCount;
    }
}
