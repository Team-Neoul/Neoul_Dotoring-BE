package com.theZ.dotoring.app.chatRoom.service;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.chat.dto.ChatMessageResponsePageDTO;
import com.theZ.dotoring.app.chat.entity.ChatMessage;
import com.theZ.dotoring.app.chat.repository.MessageRepository;
import com.theZ.dotoring.app.chatRoom.dto.ChatRoomResponseDTO;
import com.theZ.dotoring.app.chatRoom.entity.ChatRoom;
import com.theZ.dotoring.app.chatRoom.repository.ChatRoomRespository;
import com.theZ.dotoring.app.memberAccount.model.MemberAccount;
import com.theZ.dotoring.app.memberAccount.repository.MemberAccountRepository;
import com.theZ.dotoring.app.memberAccount.service.MemberAccountService;
import com.theZ.dotoring.app.menti.repository.MentiRepository;
import com.theZ.dotoring.app.mento.repository.MentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRespository chatRoomRespository;

    private final MessageRepository messageRepository;

    private final MemberAccountService memberAccountService;


    /*
      기존 채팅방이 있으면 해당 채팅방 반환, 없으면 새로운 채팅방 생성후 반환
    */
    @Transactional
    public ChatRoomResponseDTO.ChatRoomDTO findRoomOrCreateRoom(MemberAccount memberAccount, String receiverName) {
        String memberNickname = memberAccountService.getMemberNickname(memberAccount);

        ChatRoom chatRoom = chatRoomRespository.findByUserNameAndReceiverName(memberNickname, receiverName).orElse(null);

        if (chatRoom != null){
            return ChatRoomResponseDTO.from(chatRoom);
        }

        if (memberAccount.getMemberType().toString().equals("MENTO")){
            ChatRoom chatRoomOP = ChatRoom.of(memberNickname, receiverName);

            return ChatRoomResponseDTO.from(chatRoomRespository.save(chatRoomOP));
        }

        ChatRoom chatRoomOP = ChatRoom.of(receiverName, memberNickname);

        return ChatRoomResponseDTO.from(chatRoomRespository.save(chatRoomOP));
    }

    @Transactional
    public String getRoomName(String mentoName, String menteeName){

        ChatRoom chatRoom = chatRoomRespository.findByUserNameAndReceiverName(mentoName, menteeName).orElse(null);

        if (chatRoom != null){
            return chatRoom.getRoomName();
        }

        ChatRoom chatRoomOP = ChatRoom.of(mentoName, menteeName);

        return chatRoomRespository.save(chatRoomOP).getRoomName();

    }


    @Transactional
    public List<ChatRoom> findAllRooms(MemberAccount memberAccount){

        // getMemberNickName 찾기
        return chatRoomRespository.findChatRoomsByVisitedUserName(memberAccountService.getMemberNickname(memberAccount));
    }

    @Transactional
    public ChatMessageResponsePageDTO findMessageByRoom(MemberAccount memberAccount, String roomName){

        List<ChatMessage> messages = messageRepository.findByRoomNameOrderByCreatedAtDesc(roomName);

        String memberNickname = memberAccountService.getMemberNickname(memberAccount);

        // nickName 넘겨야함
        return new ChatMessageResponsePageDTO(messages, memberNickname);
    }
}