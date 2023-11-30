package com.theZ.dotoring.app.mento.handler;

import com.theZ.dotoring.app.field.service.FieldService;
import com.theZ.dotoring.app.major.service.MajorService;
import com.theZ.dotoring.app.mento.dto.UpdateMentoRqDTO;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.app.mento.service.TempMentoService;
import com.theZ.dotoring.common.S3Connector;
import com.theZ.dotoring.common.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UpdateMentoHandler {

    private final FieldService fieldService;
    private final MajorService majorService;
    private final S3Connector s3Connector;
    private final TempMentoService tempMentoService;
    private final MentoService mentoService;

    @Transactional(rollbackFor = IOException.class)
    public void execute(Long mentoId, UpdateMentoRqDTO updateMentoRqDTO) throws IOException {
        fieldService.validFields(updateMentoRqDTO.getFields());
        majorService.validMajors(updateMentoRqDTO.getMajors());
        UploadFile uploadFile = s3Connector.storeCertificate(updateMentoRqDTO.getCertificateFile());
        tempMentoService.saveTempMento(mentoId, uploadFile, updateMentoRqDTO);
        mentoService.updateWait(mentoId);
    }

}
