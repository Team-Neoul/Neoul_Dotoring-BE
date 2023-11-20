package com.theZ.dotoring.app.chat.controller;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.chat.dto.ChatMessageReponseDTO;
import com.theZ.dotoring.app.chat.dto.ChatMessageRequestDTO;
import com.theZ.dotoring.app.chat.service.MessageService;
import com.theZ.dotoring.app.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    /*
    @MessageMapping() - 위의 prefix, endpoint 설정을 포함한 입력한 url로 발행된 메세지 구독
    @SendTo() - 해당 메서드의 return값을 url을 구독하는 클라이언트에게 메세지 발행
    @DestinationVariable - 구독 및 발행 url 의 pathparameter
    @Payload - 수신된 메세지의 데이터
     */
    @MessageMapping("/{roomName}")
    @SendTo("/app/chat/message/{roomName}")
    public ChatMessageReponseDTO.ChatDTO sendMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @DestinationVariable String roomName,
            @Payload ChatMessageRequestDTO chatMessage
    )
    {
        return messageService.saveMessage(memberDetails.getMemberAccount() , chatMessage);
    }

}
