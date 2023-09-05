package com.theZ.dotoring.app.letter.handler.menti;

import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.dto.RoomResponseDTO;
import com.theZ.dotoring.app.room.mapper.RoomMapper;
import com.theZ.dotoring.app.room.service.RoomService;
import com.theZ.dotoring.exception.NotFoundRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetRoomByMentiHandler {

    private final RoomService roomService;

    private final MentiService mentiService;

    @Transactional
    public List<RoomResponseDTO> execute(Long mentiId) throws NotFoundRoomException {
        List<Room> room = roomService.findAllByMentiId(mentiId);
        Menti menti = mentiService.findMenti(mentiId);

        return RoomMapper.INSTANCE.toDTOsFromMenti(room, menti);
    }
}
