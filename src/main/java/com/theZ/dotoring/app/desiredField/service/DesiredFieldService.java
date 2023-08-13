package com.theZ.dotoring.app.desiredField.service;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import com.theZ.dotoring.app.desiredField.repository.DesiredFieldRepository;
import com.theZ.dotoring.app.field.model.Field;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class DesiredFieldService {

    private final DesiredFieldRepository desiredFieldRepository;

    public List<DesiredField> save(List<Field> fields){
        List<DesiredField> desiredFields = DesiredField.createDesiredFields(fields);
        List<DesiredField> savedDesiredFields = desiredFieldRepository.saveAll(desiredFields);
        return savedDesiredFields;
    }
}
