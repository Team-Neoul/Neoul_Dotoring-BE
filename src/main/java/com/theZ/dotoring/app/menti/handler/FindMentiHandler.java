package com.theZ.dotoring.app.menti.handler;

import com.theZ.dotoring.app.menti.dto.FindMentiByIdRespDTO;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.common.URLService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindMentiHandler {

    private final MentiService mentiService;
    private final URLService URLService;

    @Transactional
    public FindMentiByIdRespDTO execute(Long mentoId){
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.findMentiWithProfile(mentoId);
        return URLService.getFindMentiRespDTO(findMentiByIdRespDTO);
    }
}
