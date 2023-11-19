package com.theZ.dotoring.app.chat.dto;

import com.theZ.dotoring.app.chat.entity.ChatMessage;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
public class ChatMessageResponsePageDTO {

    private List<ChatMessageResp> contents;

    public ChatMessageResponsePageDTO(List<ChatMessage> chatMessages, MemberAccount memberAccount) {
        this.contents = chatMessages.stream()
                .map(chatMessage -> ChatMessageResponsePageDTO.from(chatMessage, memberAccount.getLoginId()))
                .collect(Collectors.toList());
    }

    @Getter
    @Builder
    public static class ChatMessageResp {

        private Long id;

        private boolean writer;

        private String senderName;

        private String message;

        private LocalDateTime createdAt;

    }

    public static ChatMessageResp from(ChatMessage message, String memberName) {
        return ChatMessageResp.builder()
                .id(message.getId())
                .senderName(message.getSenderName())
                .writer(Objects.equals(message.getSenderName(), memberName))
                .message(message.getMessage())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
