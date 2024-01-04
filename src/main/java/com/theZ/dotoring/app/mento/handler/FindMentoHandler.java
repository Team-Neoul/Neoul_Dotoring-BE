package com.theZ.dotoring.app.mento.handler;

import com.theZ.dotoring.app.mento.dto.FindMentoByIdRespDTO;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.common.URLService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindMentoHandler {

    private final MentoService mentoService;
    private final URLService URLService;

    @Transactional
    public FindMentoByIdRespDTO execute(Long mentoId){
        FindMentoByIdRespDTO mentoWithProfile = mentoService.findMentoWithProfile(mentoId);
        return URLService.getFindMentoRespDTO(mentoWithProfile);
    }
}
