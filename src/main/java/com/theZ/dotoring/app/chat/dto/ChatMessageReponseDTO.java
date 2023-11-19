package com.theZ.dotoring.app.chat.dto;

import com.theZ.dotoring.app.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChatMessageReponseDTO {

    public List<ChatDTO> chatDTOS;

    public ChatMessageReponseDTO(List<ChatMessage> messages){
        this.chatDTOS = messages.stream().map(ChatMessageReponseDTO::from).collect(Collectors.toList());
    }

    @Getter
    @Builder
    public static class ChatDTO {

        // id랑 생성 날짜 추가해야 함.

        private Long id;

        private String type;

        private String roomName;

        private String senderName;

        private String message;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

    }

    public static ChatDTO from(ChatMessage chatMessage){
        return ChatDTO.builder()
                .id(chatMessage.getId())
                .senderName(chatMessage.getSenderName())
                .roomName(chatMessage.getRoomName())
                .createdAt(chatMessage.getCreatedAt())
                .updatedAt(chatMessage.getUpdatedAt())
                .build();
    }
}
