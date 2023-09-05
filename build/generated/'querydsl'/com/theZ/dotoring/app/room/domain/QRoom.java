package com.theZ.dotoring.app.room.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = -2085241505L;

    public static final QRoom room = new QRoom("room");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> lastSendTime = createDateTime("lastSendTime", java.time.LocalDateTime.class);

    public final ListPath<com.theZ.dotoring.app.letter.domain.Letter, com.theZ.dotoring.app.letter.domain.QLetter> letterList = this.<com.theZ.dotoring.app.letter.domain.Letter, com.theZ.dotoring.app.letter.domain.QLetter>createList("letterList", com.theZ.dotoring.app.letter.domain.Letter.class, com.theZ.dotoring.app.letter.domain.QLetter.class, PathInits.DIRECT2);

    public final StringPath receiverName = createString("receiverName");

    public final StringPath writerName = createString("writerName");

    public QRoom(String variable) {
        super(Room.class, forVariable(variable));
    }

    public QRoom(Path<? extends Room> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoom(PathMetadata metadata) {
        super(Room.class, metadata);
    }

}

