package com.theZ.dotoring.app.menti.service;

import com.theZ.dotoring.app.menti.dto.UpdateMentiRqDTO;
import com.theZ.dotoring.app.menti.model.TempMenti;
import com.theZ.dotoring.app.menti.repository.TempMentiRepository;
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
public class TempMentiService {

    private final TempMentiRepository tempMentiRepository;

    public void saveTempMeni(Long mentiId, UploadFile uploadFile, UpdateMentiRqDTO updateMentiRqDTO) {
        TempMenti tempMenti = TempMenti.createTempMenti(mentiId, StringListUtils.attach(updateMentiRqDTO.getFields()), StringListUtils.attach(updateMentiRqDTO.getMajors()), updateMentiRqDTO.getGrade(), updateMentiRqDTO.getSchool(), uploadFile.getOriginalFileName(), uploadFile.getStoreFileName());
        tempMentiRepository.save(tempMenti);
    }
}
