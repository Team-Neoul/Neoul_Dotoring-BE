package com.theZ.dotoring.app.mento.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageableMentoDTO {

    private List<MentoRankDTO> mentoRankDTOs;
    private Boolean hasNext;
    private int size;

    @Builder
    public PageableMentoDTO(List<MentoRankDTO> mentoRankDTOs, Boolean hasNext, int size) {
        this.mentoRankDTOs = mentoRankDTOs;
        this.hasNext = hasNext;
        this.size = size;
    }
}
