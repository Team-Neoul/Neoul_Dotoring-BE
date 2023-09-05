package com.theZ.dotoring.app.letter.mapper;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-05T14:30:25+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class LetterMapperImpl implements LetterMapper {

    @Override
    public Letter toEntity(LetterByMemberRequestDTO letterRequestDTO, String writerName) {
        if ( letterRequestDTO == null && writerName == null ) {
            return null;
        }

        Letter.LetterBuilder letter = Letter.builder();

        if ( letterRequestDTO != null ) {
            letter.content( letterRequestDTO.getContent() );
        }
        letter.writerName( writerName );

        return letter.build();
    }

    @Override
    public ArrayList<LetterByMemberResponseDTO> toDTOs(List<Letter> letter, String writerName) {
        if ( letter == null ) {
            return null;
        }

        ArrayList<LetterByMemberResponseDTO> arrayList = new ArrayList<LetterByMemberResponseDTO>();
        for ( Letter letter1 : letter ) {
            arrayList.add( toDTO( letter1, writerName ) );
        }

        return arrayList;
    }

    @Override
    public LetterByMemberResponseDTO toDTO(Letter letter, String writerName) {
        if ( letter == null ) {
            return null;
        }

        LetterByMemberResponseDTO.LetterByMemberResponseDTOBuilder letterByMemberResponseDTO = LetterByMemberResponseDTO.builder();

        letterByMemberResponseDTO.letterId( letter.getId() );
        if ( letter.getCreatedAt() != null ) {
            letterByMemberResponseDTO.createdAt( Date.from( letter.getCreatedAt().toInstant( ZoneOffset.UTC ) ) );
        }
        letterByMemberResponseDTO.writer( map( letter.getWriterName(), writerName ) );
        letterByMemberResponseDTO.nickname( letter.getWriterName() );
        letterByMemberResponseDTO.content( letter.getContent() );

        return letterByMemberResponseDTO.build();
    }
}
