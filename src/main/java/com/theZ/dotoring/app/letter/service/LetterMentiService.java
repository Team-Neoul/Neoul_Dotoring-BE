package com.theZ.dotoring.app.letter.service;

import com.theZ.dotoring.app.letter.domain.Letter;
import com.theZ.dotoring.app.letter.dto.LetterByMemberRequestDTO;
import com.theZ.dotoring.app.letter.dto.LetterByMemberResponseDTO;
import com.theZ.dotoring.app.letter.mapper.LetterMapper;
import com.theZ.dotoring.app.letter.repository.LetterRepository;
import com.theZ.dotoring.app.menti.model.Menti;
import com.theZ.dotoring.app.room.domain.Room;
import com.theZ.dotoring.common.MessageCode;
import com.theZ.dotoring.exception.NotFoundLetterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Letter에 관한 비즈니스 로직이 담겨있습니다.
 *
 * @author Kevin
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LetterMentiService {

    private final LetterRepository letterRepository;


    /**
     * 쪽지 상세 페이지가 아닌 곳에서 쪽지를 보내는 메서드
     *
     * @param room - Handler로부터 전달받은 room 엔티티
     * @param user - API를 요청한 멘티
     * @param letterRequestDTO - 쪽지 내용이 담겨있는 DTO
     *
     * @return DB로부터 저장되고, 반환된 쪽지를 반환
     */
    @Transactional
    public Letter sendLetterWhereOut(LetterByMemberRequestDTO letterRequestDTO, Menti user, Room room) {
        Letter letter = LetterMapper.INSTANCE.toEntity(letterRequestDTO, user.getNickname());

        // 양방향 연관 관계
        letter.addLetter(room);

        room.updateLastSendTime();

        return letterRepository.save(letter);
    }

    /**
     * 쪽지 상세 페이지에서 쪽지를 보내는 메서드
     *
     * @param room - Handler로부터 전달받은 room 엔티티
     * @param user - API를 요청한 멘티
     * @param letterRequestDTO - 쪽지 내용이 담겨있는 DTO
     *
     * @return DB로부터 저장되고, 반환된 쪽지를 반환
     */
    @Transactional
    public Letter sendLetterWhereIn(LetterByMemberRequestDTO letterRequestDTO, Menti user, Room room) {
        Letter letter = LetterMapper.INSTANCE.toEntity(letterRequestDTO, user.getNickname());
        // 양방향 연관 관계
        letter.addLetter(room);

        room.updateLastSendTime();

        return letterRepository.save(letter);
    }

    /**
     * 특정 쪽지함에 있는 쪽지들을 반환하는 메서드
     *
     * @param room - Handler로부터 전달받은 room 엔티티
     * @param user - API를 요청한 멘티
     * @param pageable - 페이지네이션을 위한 Pageable 객체
     *
     * @return 페이징된 LetterByMemberResponseDTO 컬렉션을 반환
     */
    @Transactional(readOnly = true)
    public Slice<LetterByMemberResponseDTO> getLettersByOne(Menti user, Room room, Pageable pageable) throws NotFoundLetterException {
        Slice<Letter> letters = letterRepository.findByRoom(room, pageable)
                .orElseThrow(() -> new NotFoundLetterException(MessageCode.LETTER_NOT_FOUND));

        List<LetterByMemberResponseDTO> letterResponseDTOS = LetterMapper.INSTANCE.toDTOs(letters.getContent(), user.getNickname());

        return new PageImpl<>(letterResponseDTOS, pageable, letterResponseDTOS.size());
    }
}
