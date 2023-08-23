package com.theZ.dotoring.app.field.service;

import com.theZ.dotoring.app.field.repository.FieldRepository;
import com.theZ.dotoring.common.Field;
import com.theZ.dotoring.common.Major;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FieldService {

    private final FieldRepository fieldRepository;

    /**
     *  기존 분야 모두 저장
     */
    public void saveAll(){
        List<String> fieldNames = Field.getFields().stream().map(f -> f.toString()).collect(Collectors.toList());
        List<com.theZ.dotoring.app.field.model.Field> fields = com.theZ.dotoring.app.field.model.Field.createFields(fieldNames);
        fieldRepository.saveAll(fields);
    }

    public void validFields(List<String> uncertainFields){

        /**
         * uncertainFields에 중복된 값이 들어왔는 지 확인
         */


        /**
         *  uncertainField가 유효한 Field인지 확인
         */
        List<String> fields = Field.getFields().stream().map(f -> f.toString()).collect(Collectors.toList());
        if(!fields.containsAll(uncertainFields)){
            throw new IllegalArgumentException("유효하지 않은 분야입니다.");
        }
    }
    @Transactional(readOnly = true)
    public List<com.theZ.dotoring.app.field.model.Field> findFields(List<String> fields){
        List<com.theZ.dotoring.app.field.model.Field> fieldList = fieldRepository.findAllById(fields);
        return fieldList;
    }


}
