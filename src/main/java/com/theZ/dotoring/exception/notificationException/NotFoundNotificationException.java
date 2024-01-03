package com.theZ.dotoring.exception.notificationException;

import com.theZ.dotoring.common.MessageCode;

public class NotFoundNotificationException extends RuntimeException {

    public final MessageCode messageCode;

    public NotFoundNotificationException(MessageCode messageCode){
        this.messageCode = messageCode;
    }

}
