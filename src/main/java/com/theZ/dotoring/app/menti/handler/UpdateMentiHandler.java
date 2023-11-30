package com.theZ.dotoring.app.menti.handler;

import com.theZ.dotoring.app.field.service.FieldService;
import com.theZ.dotoring.app.major.service.MajorService;
import com.theZ.dotoring.app.menti.dto.UpdateMentiRqDTO;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.menti.service.TempMentiService;
import com.theZ.dotoring.common.S3Connector;
import com.theZ.dotoring.common.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UpdateMentiHandler {

    private final FieldService fieldService;
    private final MajorService majorService;
    private final S3Connector s3Connector;
    private final MentiService mentiService;
    private final TempMentiService tempMentiService;

    @Transactional(rollbackFor = IOException.class)
    public void execute(Long mentiId, UpdateMentiRqDTO updateMentiRqDTO) throws IOException {
        fieldService.validFields(updateMentiRqDTO.getFields());
        majorService.validMajors(updateMentiRqDTO.getMajors());
        UploadFile uploadFile = s3Connector.storeCertificate(updateMentiRqDTO.getCertificateFile());
        tempMentiService.saveTempMeni(mentiId, uploadFile, updateMentiRqDTO);
        mentiService.updateWait(mentiId);
    }
}
