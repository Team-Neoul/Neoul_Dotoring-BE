package com.theZ.dotoring.app.major.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMajor is a Querydsl query type for Major
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMajor extends EntityPathBase<Major> {

    private static final long serialVersionUID = 964069746L;

    public static final QMajor major = new QMajor("major");

    public final StringPath majorName = createString("majorName");

    public final ListPath<com.theZ.dotoring.app.memberMajor.model.MemberMajor, com.theZ.dotoring.app.memberMajor.model.QMemberMajor> memberMajors = this.<com.theZ.dotoring.app.memberMajor.model.MemberMajor, com.theZ.dotoring.app.memberMajor.model.QMemberMajor>createList("memberMajors", com.theZ.dotoring.app.memberMajor.model.MemberMajor.class, com.theZ.dotoring.app.memberMajor.model.QMemberMajor.class, PathInits.DIRECT2);

    public QMajor(String variable) {
        super(Major.class, forVariable(variable));
    }

    public QMajor(Path<? extends Major> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMajor(PathMetadata metadata) {
        super(Major.class, metadata);
    }

}

