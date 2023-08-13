package com.theZ.dotoring.app.field.model;

import com.theZ.dotoring.app.desiredField.model.DesiredField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Field {

    @Id
    private String fieldName;

    @OneToMany(mappedBy = "field")
    private List<DesiredField> desiredFields = new ArrayList<>();

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public static Field createField(String fieldName){
        Field field = new Field(fieldName);
        return field;
    }

    public static List<Field> createFields(List<String> fieldNames){
        List<Field> fields = new ArrayList<>();
        for(String fieldName : fieldNames){
            Field field = createField(fieldName);
            fields.add(field);
        }
        return fields;
    }
}
