package com.theZ.dotoring.app.mento.handler;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.service.DesiredFieldService;
import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.field.service.FieldService;
import com.theZ.dotoring.app.menti.dto.UpdateMentoDesiredFieldRqDTO;
import com.theZ.dotoring.app.mento.dto.FindMentoByIdRespDTO;
import com.theZ.dotoring.app.mento.service.MentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateMentoDesiredFieldHandler {

    private final MentoService mentoService;
    private final FieldService fieldService;
    private final DesiredFieldService desiredFieldService;


    // todo 과연 Handler에 transactional 애노테이션을 사용하는 것이 맞는 것인가?
    @Transactional
    public FindMentoByIdRespDTO execute(UpdateMentoDesiredFieldRqDTO updateMentoDesiredFieldRqDTO){
        // todo DesiredFiled를 하나씩 비교하여 수정해야할 것을 고르고 부분 수정할 것인지 vs 다 삭제하고 새로 만들 것 중 무엇이 더 좋은 방법인 지 고민!

        /**
         *  1안의 경우 복수 선택이 5개까지 가능하기때문에 기존에 선택한 희망 멘토링 분야의 갯수가 업데이트할 희망 멘토링 갯수보다 크거나 같거나 작을 때 케이스를 나눠서 생각해야한다.
         */

        /**
         *  2안
         */
        fieldService.validFields(updateMentoDesiredFieldRqDTO.getDesiredFields());
        List<Field> fields = fieldService.findFields(updateMentoDesiredFieldRqDTO.getDesiredFields());
        List<DesiredField> desiredFields = desiredFieldService.save(fields);

        desiredFieldService.deleteAllByMentoId(updateMentoDesiredFieldRqDTO.getMentoId());
        FindMentoByIdRespDTO findMentoByIdRespDTO = mentoService.updateDesiredFields(desiredFields, updateMentoDesiredFieldRqDTO.getMentoId());
        return  findMentoByIdRespDTO;
    }
}
