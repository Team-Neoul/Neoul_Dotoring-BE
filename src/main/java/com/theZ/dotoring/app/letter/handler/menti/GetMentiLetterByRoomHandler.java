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

/**
 * 컨트롤러에게 받은 인자들을 DB로부터 조회를 해와 엔티티로 LetterService에게 넘겨주고, 반환값을 다시 컨트롤러에게 넘겨줍니다.
 *
 * @author Kevin
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class GetMentiLetterByRoomHandler {
    final private LetterMentiService letterManagementService;

    final private MentiService mentiService ;

    final private RoomService roomService;

    /**
     * 컨트롤러로부터 받은 인자들을 이용해 DB로부터 엔티티를 조회한 후, Service에게 넘긴 후 반환 값을 컨트롤러에게 전달하는 메서드
     *
     * @param page - 페이지네이션을 위한 페이지
     * @param size - 페이지네이션을 위한 크기
     * @param roomPK - 쪽지를 보낸 쪽지 함
     * @param mentiId - API를 요청한 유저 id
     * @exception NotFoundLetterException - 쪽지가 존재하지 않는다면 발생하는 예외
     *
     * @return LetterByMemberResponseDTO 페이지네이션 객체를 반환
     */
    @Transactional
    public Slice<LetterByMemberResponseDTO> execute(int page, int size, Long mentiId, Long roomPK) throws NotFoundLetterException {

        Room room = roomService.findByRoomId(roomPK);

        Menti user = mentiService.findMenti(mentiId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return letterManagementService.getLettersByOne(user, room, pageable);
    }
}
