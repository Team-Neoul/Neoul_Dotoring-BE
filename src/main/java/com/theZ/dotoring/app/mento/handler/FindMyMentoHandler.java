package com.theZ.dotoring.app.mento.handler;

import com.theZ.dotoring.app.mento.dto.FindMyMentoRespDTO;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.common.URLService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindMyMentoHandler {

    private final MentoService mentoService;
    private final URLService urlService;

    @Transactional
    public FindMyMentoRespDTO execute(Long mentoId){
        FindMyMentoRespDTO findMyMentoRespDTO = mentoService.findMyMentoWithProfile(mentoId);
        return urlService.getMyMentoRespDTO(findMyMentoRespDTO);
    }
}
