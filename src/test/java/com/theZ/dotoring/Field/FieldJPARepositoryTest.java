package com.theZ.dotoring.Field;

import com.theZ.dotoring.app.field.repository.FieldRepository;
import com.theZ.dotoring.common.Field;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
public class FieldJPARepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private FieldRepository fieldRepository;

    @BeforeEach
    public void setUp(){
        List<String> fieldNames = Field.getFields().stream().map(f -> f.toString()).collect(Collectors.toList());
        List<com.theZ.dotoring.app.field.model.Field> fields = com.theZ.dotoring.app.field.model.Field.createFields(fieldNames);
        fieldRepository.saveAll(fields);
        em.flush();
    }

    @Test
    public void field_find_test(){
        List<String> findField = new ArrayList<>();
        findField.add("진로");
        findField.add("개발_언어");
        List<com.theZ.dotoring.app.field.model.Field> fieldList = fieldRepository.findAllById(findField);
        Assertions.assertThat(fieldList.size()).isEqualTo(2);
    }

}
