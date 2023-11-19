package com.theZ.dotoring.app.chatRoom.repository;

import com.theZ.dotoring.app.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRespository extends JpaRepository<ChatRoom, Long> {

    /*
      기존 채팅방이 있으면 해당 채팅방 반환, 없으면 새로운 채팅방 생성후 반환
    */
    @Query("select cr " +
            "from ChatRoom cr " +
            "where cr.menteeName = :userName or cr.mentoName = :receiverName " +
            "and cr.mentoName = :receiverName or cr.menteeName = :userName ")
    Optional<ChatRoom> findByUserNameAndReceiverName(String userName, String receiverName);

    Optional<ChatRoom> findByRoomName(String roomName);

    @Query("SELECT distinct cr FROM ChatRoom cr WHERE cr.menteeName = :userName or cr.mentoName = :userName ORDER BY cr.updatedAt DESC")
    List<ChatRoom> findChatRoomsByVisitedUserName(String userName);
}
