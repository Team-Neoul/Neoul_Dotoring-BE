package com.theZ.dotoring.app.letter.handler.mento;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.mapper.LetterMapper;
import com.theZ.dotoring.app.letter.service.LetterMentoService;
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
public class CreateLetterByMentoHandler {

    final private LetterMentoService letterManagementService;

    final private MentoService mentoService;

    final private MentiService mentiService;

    final private RoomService roomService;

    @Transactional
    public LetterByMemberResponseDTO execute(LetterByMemberRequestDTO letterRequestDTO, Long mentoId, Long mentiId){

        // findMento()가 어떤 식으로 변경되었는지 -> 사용하는 입장에서는 변화에 대응하기 어렵다.
        Mento user = mentoService.findMento(mentoId);

        Menti menti = mentiService.findMenti(mentiId);

        Room room = roomService.findOrCreateRoomByMento(user, menti);

        Letter letter = letterManagementService.sendLetterWhereOut(letterRequestDTO, user, room);

        LetterByMemberResponseDTO letterResponseDTO = LetterMapper.INSTANCE.toDTO(letter, user.getNickname());

        return letterResponseDTO;
    }

}
