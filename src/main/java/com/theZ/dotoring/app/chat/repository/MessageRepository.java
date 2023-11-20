package com.theZ.dotoring.app.chat.repository;

import com.theZ.dotoring.app.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("select cm " +
            "from ChatMessage cm " +
            "where cm.roomName = :roomName " +
            "order by cm.createdAt desc ")
    List<ChatMessage> findByRoomNameOrderByCreatedAtDesc(String roomName);

}
