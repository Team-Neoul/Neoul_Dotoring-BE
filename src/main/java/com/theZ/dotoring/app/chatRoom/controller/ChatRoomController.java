package com.theZ.dotoring.app.chatRoom.controller;

import com.theZ.dotoring.app.auth.model.MemberDetails;
import com.theZ.dotoring.app.chat.dto.ChatMessageResponsePageDTO;
import com.theZ.dotoring.app.chatRoom.dto.ChatRoomRequestDTO;
import com.theZ.dotoring.app.chatRoom.dto.ChatRoomResponseDTO;
import com.theZ.dotoring.app.chatRoom.entity.ChatRoom;
import com.theZ.dotoring.app.chatRoom.service.ChatRoomService;
import com.theZ.dotoring.common.ApiResponse;
import com.theZ.dotoring.common.ApiResponseGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

   /*
      기존 채팅방이 있으면 해당 채팅방 반환, 없으면 새로운 채팅방 생성후 반환
    */
   @PostMapping("/room")
   public ApiResponse<ApiResponse.CustomBody<ChatRoomResponseDTO.ChatRoomDTO>> findRoomOrCreateRoom(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody ChatRoomRequestDTO chatRoomRequestDTO){

        ChatRoomResponseDTO.ChatRoomDTO chatRoom = chatRoomService.findRoomOrCreateRoom(memberDetails.getMemberAccount(), chatRoomRequestDTO.getReceiverName());

       return ApiResponseGenerator.success(chatRoom, HttpStatus.OK);
   }

    // visitedNames와 현재 userName과 일치하는 채팅 방들을 최신 수정순으로 반환
    @GetMapping("/rooms")
    public ApiResponse<ApiResponse.CustomBody<ChatRoomResponseDTO>> findAllRooms(@AuthenticationPrincipal MemberDetails memberDetails){

        List<ChatRoom> chatRooms = chatRoomService.findAllRooms(memberDetails.getMemberAccount());

        return ApiResponseGenerator.success(new ChatRoomResponseDTO(chatRooms), HttpStatus.OK);
    }

    // 특정 roomName의 Message들 반환
    @GetMapping("/rooms/{roomName}")
    public ApiResponse<ApiResponse.CustomBody<ChatMessageResponsePageDTO>> findMessageByRoom(@AuthenticationPrincipal MemberDetails memberDetails, @PathVariable("roomName") String roomName){

        ChatMessageResponsePageDTO chatMessages = chatRoomService.findMessageByRoom(memberDetails.getMemberAccount(), roomName);

        return ApiResponseGenerator.success(chatMessages, HttpStatus.OK);
    }
}