package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;

public class InputNotFoundException extends RuntimeException {

    public final MessageCode messageCode;

    public InputNotFoundException(MessageCode messageCode){
        this.messageCode = messageCode;
    };

}
