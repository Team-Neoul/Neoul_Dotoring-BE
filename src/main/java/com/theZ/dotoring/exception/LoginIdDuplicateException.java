package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class LoginIdDuplicateException extends RuntimeException{

    public final MessageCode messageCode;

    public LoginIdDuplicateException(MessageCode messageCode) {
        this.messageCode = messageCode;
    }

}