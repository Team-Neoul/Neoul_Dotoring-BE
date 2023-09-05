package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class NotFoundRoomException extends RuntimeException {

    public final MessageCode messageCode;

    public NotFoundRoomException(MessageCode messageCode) { this.messageCode = messageCode; }
}
