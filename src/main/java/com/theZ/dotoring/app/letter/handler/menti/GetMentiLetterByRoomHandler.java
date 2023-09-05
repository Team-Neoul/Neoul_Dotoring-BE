package com.theZ.dotoring.app.letter.handler.menti;

import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.service.LetterMentiService;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.service.MentiService;
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
public class GetMentiLetterByRoomHandler {
    final private LetterMentiService letterManagementService;

    final private MentiService mentiService ;

    final private RoomService roomService;

    @Transactional
    public Slice<LetterByMemberResponseDTO> execute(int page, int size, Long mentiId, Long roomPK) throws NotFoundLetterException {

        Room room = roomService.findByRoomId(roomPK);

        Menti user = mentiService.findMenti(mentiId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return letterManagementService.getLettersByOne(user, room, pageable);
    }
}
