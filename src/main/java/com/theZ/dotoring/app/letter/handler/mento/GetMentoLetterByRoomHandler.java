package com.theZ.dotoring.app.letter.handler.mento;

import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.service.LetterMentoService;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.service.RoomService;
import com.theZ.dotoring.exception.NotFoundLetterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GetMentoLetterByRoomHandler {
    final private LetterMentoService letterManagementService;

    final private MentoService mentoService ;

    final private RoomService roomService;

    @Transactional
    public Slice<LetterByMemberResponseDTO> execute(int page, int size, Long roomPK, Long mentoId) throws NotFoundLetterException {

        Room room = roomService.findByRoomId(roomPK);

        Mento user = mentoService.findMento(mentoId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return letterManagementService.getLettersByOne(user, room, pageable);
    }
}
