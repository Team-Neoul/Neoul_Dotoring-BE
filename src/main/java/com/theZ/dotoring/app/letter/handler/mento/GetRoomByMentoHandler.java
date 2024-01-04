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

/**
 * 컨트롤러에게 받은 인자들을 DB로부터 조회를 해와 엔티티로 LetterService에게 넘겨주고, 반환값을 다시 컨트롤러에게 넘겨줍니다.
 *
 * @author Kevin
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class GetRoomByMentoHandler {

    private final RoomService roomService;
    private final MentoService mentoService;

    /**
     * 컨트롤러로부터 받은 인자들을 이용해 DB로부터 엔티티를 조회한 후, Service에게 넘긴 후 반환 값을 컨트롤러에게 전달하는 메서드
     *
     * @param mentoId - API 요청을 보낸 유저의 id
     * @exception NotFoundRoomException - 쪽지함이 존재하지 않는다면 발생하는 예외
     *
     * @return RoomResponseDTO 컬렉션 객체를 반환
     */
    @Transactional
    public List<RoomResponseDTO> execute(Long mentoId) throws NotFoundRoomException {
        List<Room> room = roomService.findAllByMentoId(mentoId);

        Mento mento = mentoService.findMento(mentoId);

        return RoomMapper.INSTANCE.toDTOsFromMento(room, mento);
    }
}
