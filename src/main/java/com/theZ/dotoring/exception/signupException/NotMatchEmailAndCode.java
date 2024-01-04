package com.theZ.dotoring.exception.signupException;

import com.theZ.dotoring.common.MessageCode;
import lombok.Getter;

@Getter
public class NotMatchEmailAndCode extends RuntimeException{

    public final MessageCode messageCode;

    public NotMatchEmailAndCode(MessageCode messageCode) {
        this.messageCode = messageCode;
    }
}
