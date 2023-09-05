package com.theZ.dotoring.app.letter.mapper;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.mento.model.Mento;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LetterMapper {
    // 매퍼 클래스에서 MentoMapper를 찾을 수 있도록 하는 코드
    LetterMapper INSTANCE = Mappers.getMapper(LetterMapper.class);

    // LetterRequestDto -> Letter 매핑
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(source = "writerName", target = "writerName")
    @Mapping(source = "letterRequestDTO.content", target = "content")
    Letter toEntity(LetterByMemberRequestDTO letterRequestDTO, String writerName);


    // List<Room> -> List<RoomResponseDto> 매핑, 이 때 반드시 Room -> RoomResponseDto 메서드가 먼저 있어야 한다.
    ArrayList<LetterByMemberResponseDTO> toDTOs(List<Letter> letter, @Context String writerName);

    @Mapping(source = "letter.id", target = "letterId")
    @Mapping(source = "letter.createdAt", target = "createdAt")
    @Mapping(source = "letter.writerName", target = "writer", qualifiedByName = "map")
    @Mapping(source = "letter.writerName", target = "nickname")
    LetterByMemberResponseDTO toDTO(Letter letter, @Context String writerName);

    @Named("map")
    default Boolean map (String writerName, @Context String curWriterName){
        return writerName.equals(curWriterName);
    }
}