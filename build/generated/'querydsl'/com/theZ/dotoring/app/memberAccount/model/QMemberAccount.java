package com.theZ.dotoring.app.memberAccount.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAccount is a Querydsl query type for MemberAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAccount extends EntityPathBase<MemberAccount> {

    private static final long serialVersionUID = 135249254L;

    public static final QMemberAccount memberAccount = new QMemberAccount("memberAccount");

    public final com.theZ.dotoring.common.QCommonEntity _super = new com.theZ.dotoring.common.QCommonEntity(this);

    public final ListPath<com.theZ.dotoring.app.certificate.model.Certificate, com.theZ.dotoring.app.certificate.model.QCertificate> certificates = this.<com.theZ.dotoring.app.certificate.model.Certificate, com.theZ.dotoring.app.certificate.model.QCertificate>createList("certificates", com.theZ.dotoring.app.certificate.model.Certificate.class, com.theZ.dotoring.app.certificate.model.QCertificate.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath password = createString("password");

    public final EnumPath<com.theZ.dotoring.enums.Status> status = createEnum("status", com.theZ.dotoring.enums.Status.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QMemberAccount(String variable) {
        super(MemberAccount.class, forVariable(variable));
    }

    public QMemberAccount(Path<? extends MemberAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberAccount(PathMetadata metadata) {
        super(MemberAccount.class, metadata);
    }

}

