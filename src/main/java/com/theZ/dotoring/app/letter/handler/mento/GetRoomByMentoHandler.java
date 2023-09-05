package com.theZ.dotoring.app.letter.handler.mento;

import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.service.MentoService;
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
public class GetRoomByMentoHandler {

    private final RoomService roomService;
    private final MentoService mentoService;


    @Transactional
    public List<RoomResponseDTO> execute(Long mentoId) throws NotFoundRoomException {
        List<Room> room = roomService.findAllByMentoId(mentoId);

        Mento mento = mentoService.findMento(mentoId);

        return RoomMapper.INSTANCE.toDTOsFromMento(room, mento);
    }
}
