package com.theZ.dotoring.app.memberMajor.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberMajor is a Querydsl query type for MemberMajor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberMajor extends EntityPathBase<MemberMajor> {

    private static final long serialVersionUID = 603235838L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberMajor memberMajor = new QMemberMajor("memberMajor");

    public final com.theZ.dotoring.app.major.model.QMajor major;

    public final NumberPath<Long> memberMajorId = createNumber("memberMajorId", Long.class);

    public final com.theZ.dotoring.app.menti.model.QMenti menti;

    public final com.theZ.dotoring.app.mento.model.QMento mento;

    public QMemberMajor(String variable) {
        this(MemberMajor.class, forVariable(variable), INITS);
    }

    public QMemberMajor(Path<? extends MemberMajor> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberMajor(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberMajor(PathMetadata metadata, PathInits inits) {
        this(MemberMajor.class, metadata, inits);
    }

    public QMemberMajor(Class<? extends MemberMajor> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.major = inits.isInitialized("major") ? new com.theZ.dotoring.app.major.model.QMajor(forProperty("major")) : null;
        this.menti = inits.isInitialized("menti") ? new com.theZ.dotoring.app.menti.model.QMenti(forProperty("menti"), inits.get("menti")) : null;
        this.mento = inits.isInitialized("mento") ? new com.theZ.dotoring.app.mento.model.QMento(forProperty("mento"), inits.get("mento")) : null;
    }

}

