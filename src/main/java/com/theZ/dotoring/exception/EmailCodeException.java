package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class EmailCodeException extends RuntimeException{

    public final MessageCode messageCode;

    public EmailCodeException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
