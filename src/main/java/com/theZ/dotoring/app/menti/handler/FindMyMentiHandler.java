package com.theZ.dotoring.app.menti.handler;

import com.theZ.dotoring.app.menti.dto.FindMyMentiRespDTO;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.common.URLService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindMyMentiHandler {

    private final MentiService mentiService;
    private final URLService urlService;

    public FindMyMentiRespDTO execute(Long id) {
        FindMyMentiRespDTO findMyMentiRespDTO = mentiService.findMyMentiWithProfile(id);
        return urlService.getMyMentiRespDTO(findMyMentiRespDTO);
    }
}
