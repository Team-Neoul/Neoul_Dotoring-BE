package com.theZ.dotoring.app.room.service;

import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.menti.service.MentiService;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.mento.service.MentoService;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.repository.RoomRepository;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.NotFoundRoomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Room에 관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Kevin
 * @version 1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final MentoService mentoService;

    private final MentiService mentiService;

    /**
     * mento 입장에서 mento와 menti의 nickname으로 DB의 Room 테이블에 존재하는 데이터인지 확인 후 생성 및 조회하는 메서드
     *
     * @param mento - Handler로부터 전달받은 mento 엔티티
     * @param menti - Handler로부터 전달받은 menti 엔티티
     *
     * @return DB로부터 저장되고, 반환된 쪽지함을 반환
     */
    @Transactional
    public Room findOrCreateRoomByMento(Mento mento, Menti menti) {

        Optional<Room> room1 = roomRepository.findByWriterNameAndReceiverName(mento.getNickname(), menti.getNickname());
        Optional<Room> room2 = roomRepository.findByWriterNameAndReceiverName(menti.getNickname(), mento.getNickname());

        if (room1.isPresent()) {
            return room1.get();
        }

        if (room2.isPresent()) {
            return room2.get();
        }

        Room newRoom = makeRoomObject(mento.getNickname(), menti.getNickname());

        return saveRoom(newRoom);
    }

    /**
     * menti 입장에서 mento와 menti의 nickname으로 DB의 Room 테이블에 존재하는 데이터인지 확인 후 생성 및 조회하는 메서드
     *
     * @param menti - Handler로부터 전달받은 menti 엔티티
     * @param mento - Handler로부터 전달받은 mento 엔티티
     *
     * @return DB로부터 저장되고, 반환된 쪽지함을 반환
     */
    @Transactional
    public Room findOrCreateRoomByMenti(Menti menti, Mento mento) {

        // 만들어진 방이 하나라도 있는지 검증
        // todo 추후 Mento, Menti 분리하기
        Optional<Room> room1 = roomRepository.findByWriterNameAndReceiverName(mento.getNickname(), menti.getNickname());
        Optional<Room> room2 = roomRepository.findByWriterNameAndReceiverName(menti.getNickname(), mento.getNickname());

        if (room1.isPresent()) {
            return room1.get();
        }

        if (room2.isPresent()) {
            return room2.get();
        }

        Room newRoom = makeRoomObject(mento.getNickname(), menti.getNickname());

        return saveRoom(newRoom);
    }

    /**
     * 인자로 받은 roomId의 Room이 DB에 존재하면 반환하는 메서드
     *
     * @param roomId - Handler로부터 전달받은 roomId
     *
     * @return DB에 값이 있으면 room 엔티티 반환, 없으면 null 반환
     */
    @Transactional(readOnly = true)
    public Room findByRoomId(Long roomId) {
        return roomRepository.findById(roomId).isPresent() ?
                roomRepository.findById(roomId).get(): null;
    }

    /**
     * 인자로 받은 mentoId의 Mento의 nickName에 해당하는 모든 쪽지함들을 반환하는 메서드
     *
     * @param mentoId - Handler로부터 전달받은 mentoId
     * @exception NotFoundRoomException - 쪽지함이 없을 때 발생하는 예외
     *
     * @return DB에 값이 있으면 room 엔티티 컬렉션 반환, 없으면 null 반환
     */
    @Transactional(readOnly = true)
    public List<Room> findAllByMentoId(Long mentoId) throws NotFoundRoomException {

        return roomRepository.findAllByWriterNameOrReceiverName(mentoService.findMento(mentoId).getNickname(), mentoService.findMento(mentoId).getNickname())
                .orElseThrow(() -> new NotFoundRoomException(MessageCode.ROOM_NOT_FOUND));
    }

    /**
     * 인자로 받은 mentiId의 Menti의 nickName에 해당하는 모든 쪽지함들을 반환하는 메서드
     *
     * @param mentiId - Handler로부터 전달받은 mentiId
     * @exception NotFoundRoomException - 쪽지함이 없을 때 발생하는 예외
     *
     * @return DB에 값이 있으면 room 엔티티 컬렉션 반환, 없으면 null 반환
     */
    @Transactional(readOnly = true)
    public List<Room> findAllByMentiId(Long mentiId) throws NotFoundRoomException {

        return roomRepository.findAllByWriterNameOrReceiverName(mentiService.findMenti(mentiId).getNickname(), mentiService.findMenti(mentiId).getNickname())
                .orElseThrow(() -> new NotFoundRoomException(MessageCode.ROOM_NOT_FOUND));
    }

    /**
     * 인자로 받은 room 객체를 DB에 저장하는 메서드
     *
     * @param room - 아직 DB에 저장되지 않은 Room 객체
     *
     * @return DB로부터 저장되고, 반환된 쪽지함을 반환
     */
    private Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    /**
     * Room 객체를 생성하는 메서드
     *
     * @param requestUser - 쪽지를 전달한 user의 nickname
     * @param otherUser - 쪽지를 전달받은 user의 nickname
     *
     * @return Room 객체를 생성
     */
    private Room makeRoomObject(String requestUser, String otherUser){
        return Room.builder()
                .writerName(requestUser)
                .receiverName(otherUser)
                .build();
    }
}
