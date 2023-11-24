package com.theZ.dotoring.app.chat.service;

import com.theZ.dotoring.app.chat.dto.ChatMessageReponseDTO;
import com.theZ.dotoring.app.chat.dto.ChatMessageRequestDTO;
import com.theZ.dotoring.app.chat.entity.ChatMessage;
import com.theZ.dotoring.app.chat.repository.MessageRepository;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public ChatMessageReponseDTO.ChatDTO saveMessage(MemberAccount memberAccount, ChatMessageRequestDTO messageDTO, String roomName) {

        ChatMessage message = ChatMessage.from(messageDTO, roomName);

        messageRepository.save(message);

        return ChatMessageReponseDTO.from(message);
    }
}
