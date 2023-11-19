package com.theZ.dotoring.app.chatRoom.dto;

import com.theZ.dotoring.app.chatRoom.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDTO {

    public List<ChatRoomDTO> chatRoomResponseDTOS;

    public ChatRoomResponseDTO(List<ChatRoom> chatRooms) {
        this.chatRoomResponseDTOS = chatRooms.stream().map(ChatRoomResponseDTO::from).collect(Collectors.toList());
    }

    @Getter
    @Builder
    public static class ChatRoomDTO {
        private Long id;

        private String roomName;

        private String mentoName;

        private String mentiName;

        private String lastReceivedChat;

        private LocalDateTime createdAt;

        private LocalDateTime updateAt;
        
    }

    public static ChatRoomDTO from(ChatRoom chatRoom){
        return ChatRoomDTO
                .builder()
                .id(chatRoom.getId())
                .roomName(chatRoom.getRoomName())
                .mentoName(chatRoom.getMentoName())
                .mentiName(chatRoom.getMenteeName())
                .lastReceivedChat(chatRoom.getLastReceivedChat())
                .createdAt(chatRoom.getCreatedAt())
                .updateAt(chatRoom.getUpdatedAt())
                .build();
    }
}
