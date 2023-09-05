package com.theZ.dotoring.app.menti.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMenti is a Querydsl query type for Menti
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMenti extends EntityPathBase<Menti> {

    private static final long serialVersionUID = 770130070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMenti menti = new QMenti("menti");

    public final com.theZ.dotoring.common.QCommonEntity _super = new com.theZ.dotoring.common.QCommonEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.theZ.dotoring.app.desiredField.model.DesiredField, com.theZ.dotoring.app.desiredField.model.QDesiredField> desiredFields = this.<com.theZ.dotoring.app.desiredField.model.DesiredField, com.theZ.dotoring.app.desiredField.model.QDesiredField>createList("desiredFields", com.theZ.dotoring.app.desiredField.model.DesiredField.class, com.theZ.dotoring.app.desiredField.model.QDesiredField.class, PathInits.DIRECT2);

    public final NumberPath<Long> grade = createNumber("grade", Long.class);

    public final StringPath introduction = createString("introduction");

    public final com.theZ.dotoring.app.memberAccount.model.QMemberAccount memberAccount;

    public final ListPath<com.theZ.dotoring.app.memberMajor.model.MemberMajor, com.theZ.dotoring.app.memberMajor.model.QMemberMajor> memberMajors = this.<com.theZ.dotoring.app.memberMajor.model.MemberMajor, com.theZ.dotoring.app.memberMajor.model.QMemberMajor>createList("memberMajors", com.theZ.dotoring.app.memberMajor.model.MemberMajor.class, com.theZ.dotoring.app.memberMajor.model.QMemberMajor.class, PathInits.DIRECT2);

    public final NumberPath<Long> mentiId = createNumber("mentiId", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath preferredMentoring = createString("preferredMentoring");

    public final com.theZ.dotoring.app.profile.model.QProfile profile;

    public final StringPath school = createString("school");

    public final EnumPath<com.theZ.dotoring.enums.Status> status = createEnum("status", com.theZ.dotoring.enums.Status.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QMenti(String variable) {
        this(Menti.class, forVariable(variable), INITS);
    }

    public QMenti(Path<? extends Menti> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMenti(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMenti(PathMetadata metadata, PathInits inits) {
        this(Menti.class, metadata, inits);
    }

    public QMenti(Class<? extends Menti> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberAccount = inits.isInitialized("memberAccount") ? new com.theZ.dotoring.app.memberAccount.model.QMemberAccount(forProperty("memberAccount")) : null;
        this.profile = inits.isInitialized("profile") ? new com.theZ.dotoring.app.profile.model.QProfile(forProperty("profile")) : null;
    }

}

