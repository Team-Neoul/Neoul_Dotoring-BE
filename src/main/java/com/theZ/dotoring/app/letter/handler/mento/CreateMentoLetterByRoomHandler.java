package com.theZ.dotoring.app.letter.handler.mento;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.mapper.LetterMapper;
import com.theZ.dotoring.app.letter.service.LetterMentoService;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateMentoLetterByRoomHandler {

    final private LetterMentoService letterManagementService;

    final private MentoService mentoService;

    final private RoomService roomService;

    @Transactional
    public LetterByMemberResponseDTO execute(LetterByMemberRequestDTO letterRequestDTO, Long mentoId, Long roomId){

        Mento user = mentoService.findMento(mentoId);

        Room room = roomService.findByRoomId(roomId);

        Letter letter = letterManagementService.sendLetterWhereIn(letterRequestDTO, user, room);

        return LetterMapper.INSTANCE.toDTO(letter, user.getNickname());
    }
}
