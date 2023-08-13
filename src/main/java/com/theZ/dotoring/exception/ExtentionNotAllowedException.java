package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class ExtentionNotAllowedException extends RuntimeException {


    public final MessageCode messageCode;

    public ExtentionNotAllowedException(MessageCode messageCode){
        this.messageCode = messageCode;
    }

}

