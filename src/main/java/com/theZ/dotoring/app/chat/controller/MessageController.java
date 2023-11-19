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

    private final ChatRoomService chatRoomService;

    // todo FCM 도입시 재구현하기
    //private final FCMNotificationService fcmNotificationService;


    @MessageMapping("/{roomId}")
    @SendTo("/app/chat/message")
    public ChatMessageReponseDTO.ChatDTO sendMessage(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @DestinationVariable String roomName,
            @Payload ChatMessageRequestDTO chatMessage
    )
    {
        return messageService.saveMessage(memberDetails.getMemberAccount() , chatMessage);
    }


//    private void alertTalk(List<String> visitedNames, ChatMessageReponseDTO.ChatDTO chatMessage){
//        for (String name : visitedNames) {
//            String ans = fcmNotificationService.sendNotificationByToken(
//                    new FCMNotificationRequestDto(name, chatMessage.getSenderName() + "님이 채팅을 보내셨습니다.", chatMessage.getMessage()));
//            log.info(ans);
//        }
//    }
}
