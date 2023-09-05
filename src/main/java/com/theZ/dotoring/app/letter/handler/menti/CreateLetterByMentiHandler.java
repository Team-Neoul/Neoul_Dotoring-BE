package com.theZ.dotoring.app.letter.handler.menti;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.mapper.LetterMapper;
import com.theZ.dotoring.app.letter.service.LetterMentiService;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateLetterByMentiHandler {

    final private LetterMentiService letterService;

    final private MentoService mentoService;

    final private MentiService mentiService;

    final private RoomService roomService;

    @Transactional
    public LetterByMemberResponseDTO execute(LetterByMemberRequestDTO letterRequestDTO, Long mentoId, Long mentiId){

        Menti user = mentiService.findMenti(mentiId);

        Mento mento = mentoService.findMento(mentoId);

        Room room = roomService.findOrCreateRoomByMenti(user, mento);

        Letter letter = letterService.sendLetterWhereOut(letterRequestDTO, user, room);

        return LetterMapper.INSTANCE.toDTO(letter, user.getNickname());
    }

}
