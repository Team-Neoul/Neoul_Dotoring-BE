package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class DefaultProfileImageNotFoundException extends RuntimeException{

    public final MessageCode messageCode;
    public DefaultProfileImageNotFoundException(MessageCode messageCode){
        this.messageCode = messageCode;
    }
}
