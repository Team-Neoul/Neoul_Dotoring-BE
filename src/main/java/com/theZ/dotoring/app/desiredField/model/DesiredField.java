package com.theZ.dotoring.app.desiredField.model;

import com.theZ.dotoring.app.field.model.Field;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.mento.model.Mento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 희망 분야 엔티티 - 분야 엔티티와 멘토 및 멘티 엔티티의 다대 다 관계를 1대 다 관계로 풀어주는 엔티티
 *
 * @author Sonny
 * @version 1.0
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DesiredField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long desiredFieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_name")
    private Field field;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mento_id")
    private Mento mento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menti_id")
    private Menti menti;

    public void mappingMento(Mento mento) {
        this.mento = mento;
    }

    public void mappingMenti(Menti menti) {
        this.menti = menti;
    }

    public DesiredField(Field field) {
        this.field = field;
    }

    public static DesiredField createDesiredField(Field field){
        DesiredField desiredField = new DesiredField(field);
        return desiredField;
    }

    public static List<DesiredField> createDesiredFields(List<Field> fields){
        if(fields.isEmpty()){
            throw new IllegalArgumentException("분야가 담겨있지 않습니다.");
        }
        List<DesiredField> desiredFields = new ArrayList<>();
        for(Field field : fields){
            DesiredField desiredField = createDesiredField(field);
            desiredFields.add(desiredField);
        }
        return desiredFields;
    }

}
