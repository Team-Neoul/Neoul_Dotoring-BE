package com.theZ.dotoring.app.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageRequestDTO {

    private String message;

    private String senderName;

    private String roomName;

}
