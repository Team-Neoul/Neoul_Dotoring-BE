package com.theZ.dotoring.exception.signupException;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends RuntimeException{

    public final MessageCode messageCode;

    public EmailAlreadyExistsException(MessageCode messageCode){
        this.messageCode = messageCode;
    }
}
