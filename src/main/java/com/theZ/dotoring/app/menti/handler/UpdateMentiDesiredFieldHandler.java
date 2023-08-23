package com.theZ.dotoring.app.menti.handler;


import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.service.DesiredFieldService;
import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.field.service.FieldService;
import com.theZ.dotoring.app.menti.dto.FindMentiByIdRespDTO;
import com.theZ.dotoring.app.menti.dto.UpdateMentiDesiredFieldRqDTO;
import com.theZ.dotoring.app.menti.service.MentiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateMentiDesiredFieldHandler {

    private final MentiService mentiService;
    private final FieldService fieldService;
    private final DesiredFieldService desiredFieldService;


    @Transactional
    public FindMentiByIdRespDTO execute(UpdateMentiDesiredFieldRqDTO updateMentiDesiredFieldRqDTO){
        // todo DesiredFiled를 하나씩 비교하여 수정해야할 것을 고르고 부분 수정할 것인지 vs 다 삭제하고 새로 만들 것 중 무엇이 더 좋은 방법인 지 고민!

        fieldService.validFields(updateMentiDesiredFieldRqDTO.getDesiredFields());
        List<Field> fields = fieldService.findFields(updateMentiDesiredFieldRqDTO.getDesiredFields());
        List<DesiredField> desiredFields = desiredFieldService.save(fields);

        desiredFieldService.deleteAllByMentoId(updateMentiDesiredFieldRqDTO.getMentiId());
        FindMentiByIdRespDTO findMentiByIdRespDTO = mentiService.updateDesiredFields(desiredFields, updateMentiDesiredFieldRqDTO.getMentiId());
        return  findMentiByIdRespDTO;
    }
}
