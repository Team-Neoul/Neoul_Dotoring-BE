package com.theZ.dotoring.app.menti.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class PageableMentiDTO {

    private List<MentiRankDTO> mentiRankDTOs;
    private Boolean hasNext;
    private int size;

    @Builder
    public PageableMentiDTO(List<MentiRankDTO> mentiRankDTOs, Boolean hasNext, int size) {
        this.mentiRankDTOs = mentiRankDTOs;
        this.hasNext = hasNext;
        this.size = size;
    }
}
