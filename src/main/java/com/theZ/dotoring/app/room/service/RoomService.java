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

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final MentoService mentoService;

    private final MentiService mentiService;

    @Transactional
    public Room findOrCreateRoomByMento(Mento mento, Menti menti) {
        
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

    @Transactional(readOnly = true)
    public Room findByRoomId(Long roomId) {
        return roomRepository.findById(roomId).isPresent() ?
                roomRepository.findById(roomId).get(): null;
    }

    @Transactional(readOnly = true)
    public List<Room>  findAllByMentoId(Long mentoId) throws NotFoundRoomException {
        // 분기 처리 필요할 듯
        // 만약 멘토 Id가 room에서 writer or menti에 있는 것들 처리하기

        return roomRepository.findAllByWriterNameOrReceiverName(mentoService.findMento(mentoId).getNickname(), mentoService.findMento(mentoId).getNickname())
                .orElseThrow(() -> new NotFoundRoomException(MessageCode.ROOM_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Room> findAllByMentiId(Long mentiId) throws NotFoundRoomException {
        // 분기 처리 필요할 듯
        // 만약 멘토 Id가 room에서 writer or menti에 있는 것들 처리하기

        return roomRepository.findAllByWriterNameOrReceiverName(mentiService.findMenti(mentiId).getNickname(), mentiService.findMenti(mentiId).getNickname())
                .orElseThrow(() -> new NotFoundRoomException(MessageCode.ROOM_NOT_FOUND));
    }



    private Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    private Room makeRoomObject(String requestUser, String otherUser){
        return Room.builder()
                .writerName(requestUser)
                .receiverName(otherUser)
                .build();
    }
}
