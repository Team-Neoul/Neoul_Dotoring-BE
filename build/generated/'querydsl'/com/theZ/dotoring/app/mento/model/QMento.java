package com.theZ.dotoring.app.mento.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMento is a Querydsl query type for Mento
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMento extends EntityPathBase<Mento> {

    private static final long serialVersionUID = -990287966L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMento mento = new QMento("mento");

    public final com.theZ.dotoring.common.QCommonEntity _super = new com.theZ.dotoring.common.QCommonEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.theZ.dotoring.app.desiredField.model.DesiredField, com.theZ.dotoring.app.desiredField.model.QDesiredField> desiredFields = this.<com.theZ.dotoring.app.desiredField.model.DesiredField, com.theZ.dotoring.app.desiredField.model.QDesiredField>createList("desiredFields", com.theZ.dotoring.app.desiredField.model.DesiredField.class, com.theZ.dotoring.app.desiredField.model.QDesiredField.class, PathInits.DIRECT2);

    public final NumberPath<Long> grade = createNumber("grade", Long.class);

    public final StringPath introduction = createString("introduction");

    public final com.theZ.dotoring.app.memberAccount.model.QMemberAccount memberAccount;

    public final ListPath<com.theZ.dotoring.app.memberMajor.model.MemberMajor, com.theZ.dotoring.app.memberMajor.model.QMemberMajor> memberMajors = this.<com.theZ.dotoring.app.memberMajor.model.MemberMajor, com.theZ.dotoring.app.memberMajor.model.QMemberMajor>createList("memberMajors", com.theZ.dotoring.app.memberMajor.model.MemberMajor.class, com.theZ.dotoring.app.memberMajor.model.QMemberMajor.class, PathInits.DIRECT2);

    public final NumberPath<Long> mentoId = createNumber("mentoId", Long.class);

    public final NumberPath<Integer> mentoringCount = createNumber("mentoringCount", Integer.class);

    public final StringPath mentoringSystem = createString("mentoringSystem");

    public final StringPath nickname = createString("nickname");

    public final com.theZ.dotoring.app.profile.model.QProfile profile;

    public final StringPath school = createString("school");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QMento(String variable) {
        this(Mento.class, forVariable(variable), INITS);
    }

    public QMento(Path<? extends Mento> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMento(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMento(PathMetadata metadata, PathInits inits) {
        this(Mento.class, metadata, inits);
    }

    public QMento(Class<? extends Mento> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberAccount = inits.isInitialized("memberAccount") ? new com.theZ.dotoring.app.memberAccount.model.QMemberAccount(forProperty("memberAccount")) : null;
        this.profile = inits.isInitialized("profile") ? new com.theZ.dotoring.app.profile.model.QProfile(forProperty("profile")) : null;
    }

}

