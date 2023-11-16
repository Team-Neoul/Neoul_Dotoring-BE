package com.theZ.dotoring.app.mento.handler;

import com.theZ.dotoring.app.desiredField.service.DesiredFieldService;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.dto.CustomPageRequest;
import com.theZ.dotoring.app.mento.dto.FindAllMentoRespDTO;
import com.theZ.dotoring.app.mento.dto.PageableMentoDTO;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.common.URLConverter;
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
    private final MentiService mentiService;
    private final URLConverter URLConverter;

    public Slice<FindAllMentoRespDTO> execute(Long lastMentoId, Integer size, Long mentiId){
        String mentiNickname = getNickname(mentiId);
        PageableMentoDTO pageableMento = desiredFieldService.findPageableMento(mentiId, lastMentoId, size);
        List<Long> mentoIds = pageableMento.getMentoRankDTOs().stream().map(mentoRankDTO -> mentoRankDTO.getMentoId()).collect(Collectors.toList());
        List<FindAllMentoRespDTO> recommendMentors = mentoService.findRecommendMentos(mentoIds);
        return new SliceImpl<>(URLConverter.getFindAllMentoRespDTOS(recommendMentors), getPageRequest(mentiNickname, pageableMento), pageableMento.getHasNext());
    }

    private String getNickname(Long mentiId) {
        return mentiService.findMenti(mentiId).getNickname();
    }

    private PageRequest getPageRequest(String mentiNickname, PageableMentoDTO pageableMento) {
        PageRequest pageRequest = PageRequest.of(0, pageableMento.getSize());
        CustomPageRequest customPageRequest = CustomPageRequest.of(pageRequest, mentiNickname);
        return customPageRequest;
    }


}
