package com.theZ.dotoring.app.menti.handler;

import com.theZ.dotoring.app.desiredField.service.DesiredFieldService;
import com.theZ.dotoring.app.menti.dto.FindAllMentiRespDTO;
import com.theZ.dotoring.app.menti.dto.PageableMentiDTO;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.dto.CustomPageRequest;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.common.URLService;
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
    private final MentoService mentoService;
    private final URLService URLService;

    public Slice<FindAllMentiRespDTO> execute(Long lastMentiId, Integer size, Long mentoId){
        String mentoNickname = getNickname(mentoId);
        PageableMentiDTO pageableMenti = desiredFieldService.findPageableMenti(mentoId, lastMentiId, size);
        List<Long> mentiIds = pageableMenti.getMentiRankDTOs().stream().map(mentiRankDTO -> mentiRankDTO.getMentiId()).collect(Collectors.toList());
        List<FindAllMentiRespDTO> recommendMentis = mentiService.findRecommendMentis(mentiIds);
        return new SliceImpl<>(URLService.getFindAllMentiRespDTOS(recommendMentis), getPageRequest(mentoNickname,pageableMenti), pageableMenti.getHasNext());
    }

    private PageRequest getPageRequest(String mentoNickname, PageableMentiDTO pageableMenti) {
        PageRequest pageRequest = PageRequest.of(0, pageableMenti.getSize());
        CustomPageRequest customPageRequest = CustomPageRequest.of(pageRequest, mentoNickname);
        return customPageRequest;
    }

    private String getNickname(Long mentoId) {
        return mentoService.findMento(mentoId).getNickname();
    }
}
