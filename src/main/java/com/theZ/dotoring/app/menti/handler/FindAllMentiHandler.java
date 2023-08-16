package com.theZ.dotoring.app.menti.handler;

import com.theZ.dotoring.app.desiredField.service.DesiredFieldService;
import com.theZ.dotoring.app.menti.dto.MentiCardResponseDTO;
import com.theZ.dotoring.app.menti.dto.PageableMentiDTO;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.dto.MentoCardResponseDTO;
import com.theZ.dotoring.app.mento.dto.PageableMentoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindAllMentiHandler {

    private final MentiService mentiService;
    private final DesiredFieldService desiredFieldService;

    public Slice<MentiCardResponseDTO> execute(Long lastMentiId, Integer size, Long mentoId){

        PageableMentiDTO pageableMenti = desiredFieldService.findPageableMenti(mentoId, lastMentiId, size);
        List<Long> mentiIds = pageableMenti.getMentiRankDTOs().stream().map(mentiRankDTO -> mentiRankDTO.getMentiId()).collect(Collectors.toList());
        List<MentiCardResponseDTO> recommendMentis = mentiService.findRecommendMentis(mentiIds);
        PageRequest pageRequest = PageRequest.of(0, pageableMenti.getSize());
        return new SliceImpl<>(recommendMentis,pageRequest, pageableMenti.getHasNext());
    }
}
