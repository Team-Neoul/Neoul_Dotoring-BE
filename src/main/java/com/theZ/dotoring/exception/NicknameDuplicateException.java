package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class NicknameDuplicateException extends RuntimeException{

    public final MessageCode messageCode;

    public NicknameDuplicateException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

}
