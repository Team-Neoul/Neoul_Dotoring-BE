package com.theZ.dotoring.app.room.mapper;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.major.model.Major;
import com.theZ.dotoring.app.memberMajor.model.MemberMajor;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.mento.model.Mento;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.app.room.dto.RoomResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface RoomMapper {
    // 매퍼 클래스에서 MentoMapper를 찾을 수 있도록 하는 코드
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    // List<Room> -> List<RoomResponseDto> 매핑, 이 때 반드시 Room -> RoomResponseDto 메서드가 먼저 있어야 한다.
    // Room 엔티티 목록을 RoomResponseDTO 목록으로 변환하는 메서드
    default ArrayList<RoomResponseDTO> toDTOsFromMento(List<Room> room, Mento receiver) {
        ArrayList<RoomResponseDTO> roomResponseDTOList = new ArrayList<>();
        for (Room roomEntity : room) {
            RoomResponseDTO roomResponseDTO = toDTOFromMento(roomEntity, receiver);
            roomResponseDTOList.add(roomResponseDTO);
        }
        return roomResponseDTOList;
    }

    @Mapping(source = "room.id", target = "roomPK")
    @Mapping(source = "receiver.nickname", target = "nickname")
    @Mapping(source = "receiver.mentoId", target = "memberPK")
    @Mapping(source = "room.letterList", target = "lastLetter", qualifiedByName = "letterListToStr")
    @Mapping(source = "receiver.memberMajors", target = "major", qualifiedByName = "initMentoMajor")
    @Mapping(source = "room.lastSendTime", target = "updateAt")
    RoomResponseDTO toDTOFromMento(Room room, Mento receiver);

    @Named("initMentoMajor")
    static String initMentoMajor(List<MemberMajor> majorList) {
        return majorList.get(0).getMajor().getMajorName();
    }

    default ArrayList<RoomResponseDTO> toDTOsFromMenti(List<Room> room, Menti receiver) {
        ArrayList<RoomResponseDTO> roomResponseDTOList = new ArrayList<>();
        for (Room roomEntity : room) {
            RoomResponseDTO roomResponseDTO = toDTOFromMenti(roomEntity, receiver);
            roomResponseDTOList.add(roomResponseDTO);
        }
        return roomResponseDTOList;
    }

    @Mapping(source = "room.id", target = "roomPK")
    @Mapping(source = "receiver.nickname", target = "nickname")
    @Mapping(source = "receiver.mentiId", target = "memberPK")
    @Mapping(source = "room.letterList", target = "lastLetter", qualifiedByName = "letterListToStr")
    @Mapping(source = "receiver.memberMajors", target = "major", qualifiedByName = "initMentiMajor")
    @Mapping(source = "room.lastSendTime", target = "updateAt")
    RoomResponseDTO toDTOFromMenti(Room room, Menti receiver);

    @Named("initMentiMajor")
    static String initMentiMajor(List<MemberMajor> majorList) {
        return majorList.get(0).getMajor().getMajorName();
    }

    @Named("letterListToStr")
    static String letterListToStr(List<Letter> letterList) {
        if (letterList.isEmpty()) {
            return null;
        }
        return letterList.get(letterList.size() - 1).getContent();
    }
}