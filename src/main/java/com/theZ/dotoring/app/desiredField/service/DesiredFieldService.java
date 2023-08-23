package com.theZ.dotoring.app.desiredField.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.repository.DesiredFieldRepository;
import com.theZ.dotoring.app.desiredField.repository.QueryDesiredFieldRepository;
import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.menti.dto.PageableMentiDTO;
import com.theZ.dotoring.app.mento.dto.PageableMentoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DesiredFieldService {

    private final DesiredFieldRepository desiredFieldRepository;
    private final QueryDesiredFieldRepository queryDesiredFieldRepository;

    public List<DesiredField> save(List<Field> fields){
        List<DesiredField> desiredFields = DesiredField.createDesiredFields(fields);
        List<DesiredField> savedDesiredFields = desiredFieldRepository.saveAll(desiredFields);
        return savedDesiredFields;
    }


    public void deleteAllByMentoId(Long mentoId){
//
//        List<DesiredField> desiredFields = desiredFieldRepository.findByMentoId(mentoId);
//
//        /**
//         *  Mento에서 desiredFields로의 연관관계 해제
//         */
//        desiredFields.stream().forEach(i -> i.getMento().getDesiredFields().clear());
//        desiredFields.stream().forEach(i -> i.getField().getDesiredFields().clear());
        desiredFieldRepository.deleteAllByMento_MentoId(mentoId);
    }

    @Transactional(readOnly = true)
    public PageableMentoDTO findPageableMento(Long mentiId, Long lastMentoId, int size){
        List<String> fieldNames = desiredFieldRepository.findByMentiId(mentiId).stream().map(df -> df.getField().getFieldName()).collect(Collectors.toList());
        PageableMentoDTO pageableMento = queryDesiredFieldRepository.findPageableMento(fieldNames, lastMentoId, size);
        return pageableMento;
    }

    @Transactional(readOnly = true)
    public PageableMentiDTO findPageableMenti(Long mentoId, Long lastMentiId, int size){
        List<String> fieldNames = desiredFieldRepository.findByMentoId(mentoId).stream().map(df -> df.getField().getFieldName()).collect(Collectors.toList());
        PageableMentiDTO pageableMenti = queryDesiredFieldRepository.findPageableMenti(fieldNames, lastMentiId, size);
        return pageableMenti;
    }

}
