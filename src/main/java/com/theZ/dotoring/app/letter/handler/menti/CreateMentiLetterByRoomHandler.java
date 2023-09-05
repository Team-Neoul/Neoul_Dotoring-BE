package com.theZ.dotoring.app.letter.handler.menti;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.mapper.LetterMapper;
import com.theZ.dotoring.app.letter.service.LetterMentiService;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateMentiLetterByRoomHandler {

    final private LetterMentiService letterManagementService;

    final private MentiService mentiService;

    final private RoomService roomService;

    @Transactional
    public LetterByMemberResponseDTO execute(LetterByMemberRequestDTO letterRequestDTO, Long mentiId, Long roomId){

        Menti user = mentiService.findMenti(mentiId);

        Room room = roomService.findByRoomId(roomId);

        Letter letter = letterManagementService.sendLetterWhereIn(letterRequestDTO, user, room);

        return LetterMapper.INSTANCE.toDTO(letter, user.getNickname());
    }
}
