package com.theZ.dotoring.exception;

import com.theZ.dotoring.common.MessageCode;

public class FileSaveFailedException  extends RuntimeException{
    public final MessageCode messageCode;

    public FileSaveFailedException(MessageCode messageCode){
        this.messageCode = messageCode;
    }
}
