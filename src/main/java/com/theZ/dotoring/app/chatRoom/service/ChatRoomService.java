package com.theZ.dotoring.app.chatRoom.service;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.chat.dto.ChatMessageResponsePageDTO;
import com.theZ.dotoring.app.chat.entity.ChatMessage;
import com.theZ.dotoring.app.chat.repository.MessageRepository;
import com.theZ.dotoring.app.chatRoom.dto.ChatRoomResponseDTO;
import com.theZ.dotoring.app.chatRoom.entity.ChatRoom;
import com.theZ.dotoring.app.chatRoom.repository.ChatRoomRespository;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRespository chatRoomRespository;

    private final MessageRepository messageRepository;


    /*
      기존 채팅방이 있으면 해당 채팅방 반환, 없으면 새로운 채팅방 생성후 반환
    */
    @Transactional
    public ChatRoomResponseDTO.ChatRoomDTO findRoomOrcreateRoom(MemberAccount memberAccount, String receiverName) {
        Optional<ChatRoom> chatRoom = chatRoomRespository.findByUserNameAndReceiverName(memberAccount.getLoginId(), receiverName);

        if (chatRoom.isPresent()) {
            return ChatRoomResponseDTO.from(chatRoom.get());
        }

        /*
            추후 멘토, 멘티에 따른 분기처리 필요
         */

        ChatRoom chatRoomObj = ChatRoom.builder()
                .mentoName(memberAccount.getLoginId())
                .menteeName(receiverName)
                .build();

        chatRoomObj.setRandomRoomName();

        return ChatRoomResponseDTO.from(chatRoomRespository.save(chatRoomObj));
    }

    @Transactional
    public List<ChatRoom> findAllRooms(MemberDetails memberDetails){

        return chatRoomRespository.findChatRoomsByVisitedUserName(memberDetails.getUsername());
    }

    @Transactional
    public ChatMessageResponsePageDTO findMessageByRoom(MemberAccount memberAccount, String roomName){

        List<ChatMessage> messages = messageRepository.findByRoomNameOrderByCreatedAtDesc(roomName);

        return new ChatMessageResponsePageDTO(messages, memberAccount);
    }
}