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

/**
 * Field에관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Sonny
 * @version 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FieldService {

    private final FieldRepository fieldRepository;

    /**
     * 서버가 시작할 때, Enum Field에 정의되어 있는 분야들을 모두 Field 엔티티로 만들어 DB에 이를 저장하는 메서드
     *
     */
    public void saveAll(){
        List<String> fieldNames = Field.getFields().stream().map(f -> f.toString()).collect(Collectors.toList());
        List<com.theZ.dotoring.app.field.model.Field> fields = com.theZ.dotoring.app.field.model.Field.createFields(fieldNames);
        fieldRepository.saveAll(fields);
    }

    /**
     * 인자로 받은 uncertainFields 사용하여 이들이 유효한 Field인지 확인하는 메서드
     *
     * @param uncertainFields
     * @Exception IllegalArgumentException - 유효하지 않은 분야일 때 발생하는 예외
     *
     */
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

    /**
     * 인자로 받은 fields를 사용하여 해당 Field 엔티티들을 반환하는 메서드
     *
     * @param fields
     *
     * @return fieldList
     */
    @Transactional(readOnly = true)
    public List<com.theZ.dotoring.app.field.model.Field> findFields(List<String> fields){
        List<com.theZ.dotoring.app.field.model.Field> fieldList = fieldRepository.findAllById(fields);
        return fieldList;
    }


}
