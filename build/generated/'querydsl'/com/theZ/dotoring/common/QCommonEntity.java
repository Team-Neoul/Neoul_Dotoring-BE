package com.theZ.dotoring.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonEntity is a Querydsl query type for CommonEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QCommonEntity extends EntityPathBase<CommonEntity> {

    private static final long serialVersionUID = 1092188945L;

    public static final QCommonEntity commonEntity = new QCommonEntity("commonEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QCommonEntity(String variable) {
        super(CommonEntity.class, forVariable(variable));
    }

    public QCommonEntity(Path<? extends CommonEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonEntity(PathMetadata metadata) {
        super(CommonEntity.class, metadata);
    }

}

