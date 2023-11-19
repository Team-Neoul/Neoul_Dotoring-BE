package com.theZ.dotoring.app.chat.entity;

import com.theZ.dotoring.app.chat.dto.ChatMessageRequestDTO;
import com.theZ.dotoring.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ChatMessage extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatMessageId", nullable = false)
    private Long id;

    //채팅방 ID
    private String roomName;

    //보내는 사람
    // senderName이 나을 것 같은 이유 -> Mento, Menti 분리에도 영향을 안받기 위해
    private String senderName;

    //내용
    private String message;

    public static ChatMessage from(ChatMessageRequestDTO requestDTO){
        return ChatMessage.builder()
                .message(requestDTO.getMessage())
                .senderName(requestDTO.getSenderName())
                .roomName(requestDTO.getRoomName())
                .build();
    }

    public void updateSenderName(String username){
        this.senderName = username;
    }

    public void updateRoomName(String roomName){
        this.roomName = roomName;
    }
}