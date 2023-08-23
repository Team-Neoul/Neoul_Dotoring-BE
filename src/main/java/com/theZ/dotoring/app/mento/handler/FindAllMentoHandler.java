package com.theZ.dotoring.app.mento.handler;

import com.theZ.dotoring.app.desiredField.service.DesiredFieldService;
import com.theZ.dotoring.app.mento.dto.FindAllMentoRespDTO;
import com.theZ.dotoring.app.mento.dto.PageableMentoDTO;
import com.theZ.dotoring.app.mento.service.MentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindAllMentoHandler {

    private final MentoService mentoService;
    private final DesiredFieldService desiredFieldService;

    public Slice<FindAllMentoRespDTO> execute(Long lastMentoId, Integer size, Long mentiId){

        PageableMentoDTO pageableMento = desiredFieldService.findPageableMento(mentiId, lastMentoId, size);
        List<Long> mentoIds = pageableMento.getMentoRankDTOs().stream().map(mentoRankDTO -> mentoRankDTO.getMentoId()).collect(Collectors.toList());
        List<FindAllMentoRespDTO> recommendMentosDTO = mentoService.findRecommendMentos(mentoIds);
        PageRequest pageRequest = PageRequest.of(0, pageableMento.getSize());
        return new SliceImpl<>(recommendMentosDTO,pageRequest, pageableMento.getHasNext());
    }


}
