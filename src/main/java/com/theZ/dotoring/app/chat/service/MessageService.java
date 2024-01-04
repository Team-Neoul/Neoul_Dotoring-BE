package com.theZ.dotoring.app.chat.service;

import com.theZ.dotoring.app.chat.dto.ChatMessageReponseDTO;
import com.theZ.dotoring.app.chat.dto.ChatMessageRequestDTO;
import com.theZ.dotoring.app.chat.entity.ChatMessage;
import com.theZ.dotoring.app.chat.repository.MessageRepository;
import com.theZ.dotoring.app.chatRoom.service.ChatRoomService;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final ChatRoomService chatRoomService;

    private final MemberAccountService memberAccountService;

    @Transactional
    public ChatMessageReponseDTO.ChatDTO saveMessage(MemberAccount memberAccount, ChatMessageRequestDTO messageDTO, String roomName) {

        ChatMessage message = ChatMessage.of(messageDTO, roomName);

        messageRepository.save(message);

        return ChatMessageReponseDTO.from(message);
    }

    @Transactional
    public void sendJoinNotificationMessage(String mentoName, String mentiName, boolean isMentoChatSender, String participationForm){
        // 해당 이름을 가진 유저들의 방이 있는지 확인하고 있으면 해당 방에 메시지 전송, 없으면 방 생성 후 전송
        String chatRoomName = chatRoomService.getRoomName(mentoName, mentiName);

        if (isMentoChatSender) {
            // 멘토를 sender로
            ChatMessage message = ChatMessage.of(participationForm, mentoName, chatRoomName);
            messageRepository.save(message);
        }

        if (!isMentoChatSender) {
            // 멘티를 sender로
            ChatMessage message = ChatMessage.of(participationForm, mentiName, chatRoomName);
            messageRepository.save(message);
        }
    }
}
