package com.theZ.dotoring.app.field.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QField is a Querydsl query type for Field
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QField extends EntityPathBase<Field> {

    private static final long serialVersionUID = -1628141964L;

    public static final QField field = new QField("field");

    public final ListPath<com.theZ.dotoring.app.desiredField.model.DesiredField, com.theZ.dotoring.app.desiredField.model.QDesiredField> desiredFields = this.<com.theZ.dotoring.app.desiredField.model.DesiredField, com.theZ.dotoring.app.desiredField.model.QDesiredField>createList("desiredFields", com.theZ.dotoring.app.desiredField.model.DesiredField.class, com.theZ.dotoring.app.desiredField.model.QDesiredField.class, PathInits.DIRECT2);

    public final StringPath fieldName = createString("fieldName");

    public QField(String variable) {
        super(Field.class, forVariable(variable));
    }

    public QField(Path<? extends Field> path) {
        super(path.getType(), path.getMetadata());
    }

    public QField(PathMetadata metadata) {
        super(Field.class, metadata);
    }

}

