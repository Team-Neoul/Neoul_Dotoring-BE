package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class NotFoundMemberException extends RuntimeException {

    public final MessageCode messageCode;

    public NotFoundMemberException(MessageCode messageCode) { this.messageCode = messageCode; }
}
