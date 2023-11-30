package com.theZ.dotoring.app.mento.service;

import com.theZ.dotoring.app.mento.dto.UpdateMentoRqDTO;
import com.theZ.dotoring.app.mento.model.TempMento;
import com.theZ.dotoring.app.mento.repository.TempMentoRepository;
import com.theZ.dotoring.common.StringListUtils;
import com.theZ.dotoring.common.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TempMentoService {

    private final TempMentoRepository tempMentoRepository;

    @Transactional
    public void saveTempMento(Long mentoId, UploadFile uploadFile, UpdateMentoRqDTO updateMentoRqDTO) {
        TempMento tempMento = TempMento.createTempMento(mentoId, StringListUtils.attach(updateMentoRqDTO.getFields()), StringListUtils.attach(updateMentoRqDTO.getMajors()), updateMentoRqDTO.getGrade(), updateMentoRqDTO.getSchool(), uploadFile.getOriginalFileName(), uploadFile.getStoreFileName());
        tempMentoRepository.save(tempMento);
    }
}
