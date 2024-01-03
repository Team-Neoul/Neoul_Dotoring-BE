package com.theZ.dotoring.exception.notificationException;

import com.theZ.dotoring.common.MessageCode;

public class DuplicateParticipationException extends RuntimeException {

    public final MessageCode messageCode;

    public DuplicateParticipationException(MessageCode messageCode){
        this.messageCode = messageCode;
    }

}
