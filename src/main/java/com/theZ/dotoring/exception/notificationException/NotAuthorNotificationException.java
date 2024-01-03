package com.theZ.dotoring.exception.notificationException;

import com.theZ.dotoring.common.MessageCode;

public class NotAuthorNotificationException extends RuntimeException {

    public final MessageCode messageCode;

    public NotAuthorNotificationException(MessageCode messageCode){
        this.messageCode = messageCode;
    }

}
