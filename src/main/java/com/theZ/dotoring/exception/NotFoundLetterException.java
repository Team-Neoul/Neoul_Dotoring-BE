package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class NotFoundLetterException extends RuntimeException {

    public final MessageCode messageCode;

    public NotFoundLetterException(MessageCode messageCode) { this.messageCode = messageCode; }

}
