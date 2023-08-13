package com.theZ.dotoring.app.desiredField.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDesiredField is a Querydsl query type for DesiredField
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDesiredField extends EntityPathBase<DesiredField> {

    private static final long serialVersionUID = -1756565050L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDesiredField desiredField = new QDesiredField("desiredField");

    public final NumberPath<Long> desiredFieldId = createNumber("desiredFieldId", Long.class);

    public final com.theZ.dotoring.app.field.model.QField field;

    public final com.theZ.dotoring.app.mento.model.QMento mento;

    public QDesiredField(String variable) {
        this(DesiredField.class, forVariable(variable), INITS);
    }

    public QDesiredField(Path<? extends DesiredField> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDesiredField(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDesiredField(PathMetadata metadata, PathInits inits) {
        this(DesiredField.class, metadata, inits);
    }

    public QDesiredField(Class<? extends DesiredField> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.field = inits.isInitialized("field") ? new com.theZ.dotoring.app.field.model.QField(forProperty("field")) : null;
        this.mento = inits.isInitialized("mento") ? new com.theZ.dotoring.app.mento.model.QMento(forProperty("mento"), inits.get("mento")) : null;
    }

}

